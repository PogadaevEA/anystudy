package com.roxyfy.serialization.unmarshalling;



import com.roxyfy.exceptions.XmlUnmarshallingException;

import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

/**
 * This class handles XML events and creates an object based on them.
 * It expects that the first event it receives will be the opening tag of the element it parses.
 * <p>
 * Then it acts according to the following logic:
 * <ol>
 *     <li>
 *     </li>
 *     <li>
 *         If the element has internal text (not nested elements), then it should not contain nested elements.
 *         In case if the element has internal text and attributes,
 *         In case if the element has only internal text, the result of parsing will be the inner text of the element.
 *     </li>
 *     <li>
 *         If an element has nested elements,
 *         they will be parsed by another instance of the {@link ObjectParser} and added to the resulting map with the element name as the key.
 *     </li>
 * </ol>
 * <p>
 * The result can be the internal text of the element (if it does not have nested elements and attributes) or a map,
 * the keys of which will be the names of nested elements, and the values will be the result of their parsing.
 */
public class ObjectParser {

    private State state = State.READY;
    private ObjectParser nestedElementParser;
    private final UnmarshallingResult result = new UnmarshallingResult();

    public void handleElementStart(Map<String, String> attributes, Deque<String> path) throws XmlUnmarshallingException {
        checkImpossibleState(State.FINISHED);

        if (hasNestedElementParser()) {
            nestedElementParser.handleElementStart(attributes, path);
            return;
        }

        if (state == State.READY) {
            result.setAttributes(attributes);

            state = State.STARTED;
            return;
        }

        if (state == State.PARSING_INNER_TEXT) {
            throw new XmlUnmarshallingException("Elements that have both inner text and nested elements are not supported.", path);
        }

        if (state == State.STARTED) {
            state = State.PARSING_ELEMENTS;
        }

        nestedElementParser = new ObjectParser();
        nestedElementParser.handleElementStart(attributes, path);
    }

    public void handleElementEnd(String elementName) {
        checkImpossibleState(State.READY, State.FINISHED);

        if (state == State.PARSING_INNER_TEXT || state == State.STARTED || !hasNestedElementParser()) {
            state = State.FINISHED;
            return;
        }

        nestedElementParser.handleElementEnd(elementName);

        if (!hasNestedElementParser()) {
            result.addElement(elementName, nestedElementParser.getResult());
        }
    }

    public void handleInnerText(String text, Deque<String> path) throws XmlUnmarshallingException {
        checkImpossibleState(State.READY, State.FINISHED);

        if (hasNestedElementParser()) {
            nestedElementParser.handleInnerText(text, path);
            return;
        }

        if (text.isBlank()) {
            result.addTextPart(text);
            return;
        }

        if (state == State.PARSING_ELEMENTS) {
            throw new XmlUnmarshallingException("Elements that have both nested elements and inner text are not supported.", path);
        }

        state = State.PARSING_INNER_TEXT;
        result.addTextPart(text);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFinished() {
        return state == State.FINISHED;
    }

    public Object getResult() {
        if (!isFinished()) {
            throw new IllegalStateException("Parsing is not finished yet");
        }

        return result.toObject();
    }

    private void checkImpossibleState(State... states) {
        if (Arrays.asList(states).contains(state)) {
            throw new IllegalStateException("Impossible state: " + state);
        }
    }

    private boolean hasNestedElementParser() {
        return nestedElementParser != null && !nestedElementParser.isFinished();
    }

    private enum State {

        READY,
        STARTED,
        PARSING_ELEMENTS,
        PARSING_INNER_TEXT,
        FINISHED
    }
}
