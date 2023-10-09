package com.roxyfy.serialization.marshalling;

import lombok.RequiredArgsConstructor;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import java.util.Collections;
import java.util.Map;

/**
 * Wrapper around {@link XMLEventWriter} to hide unnecessary details.
 */
@RequiredArgsConstructor
public class XmlEventsWriter {

    private final XMLEventWriter writer;
    private final XMLEventFactory factory = XMLEventFactory.newInstance();

    public void writeDocumentStart() throws XMLStreamException {
        writer.add(factory.createStartDocument("UTF-8", "1.0"));
    }

    public void writeDocumentEnd() throws XMLStreamException {
        writer.add(factory.createEndDocument());
        // For the same java version, but from different providers could encapsulate some code optimisation while
        // compiling to byte code. That could be a reason of different behavior of when buffered data is written
        // to their intended destination. Direct flush() call leads to uniformity.
        writer.flush();
    }

    public void writeElementStart(String elementName, Map<String, String> attributes) throws XMLStreamException {
        var attributesList = attributes.entrySet().stream()
                .map(entry -> factory.createAttribute(entry.getKey(), entry.getValue()))
                .iterator();
        writer.add(factory.createStartElement("", "", elementName, attributesList, Collections.emptyIterator()));
    }

    public void writeElementEnd(String elementName) throws XMLStreamException {
        writer.add(factory.createEndElement("", "", elementName));
    }

    public void writeInnerText(String text) throws XMLStreamException {
        writer.add(factory.createCharacters(text));
    }
}
