package com.roxyfy.serialization.marshalling;

import com.roxyfy.exceptions.XmlMarshallingException;
import com.roxyfy.model.XmlDynamicDto;
import com.roxyfy.serialization.XmlConstants;
import com.roxyfy.serialization.XmlDynamicTypeDescriptor;
import lombok.RequiredArgsConstructor;
import org.springframework.oxm.Marshaller;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Note that there is no validation of map keys, it must be a valid XML name.
 *
 * @param <TYPE> type of supported instances
 */
@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
public class XmlDynamicDtoMarshaller<TYPE extends XmlDynamicDto> implements Marshaller {

    private final XmlDynamicTypeDescriptor<TYPE> typeDescriptor;

    @Override
    public boolean supports(Class<?> clazz) {
        return typeDescriptor.getTypeClass().isAssignableFrom(clazz);
    }

    @Override
    public void marshal(Object graph, Result result) {
        if (typeDescriptor.getTypeClass().isInstance(graph) && graph instanceof XmlDynamicDto dto) {
            marshalFields(dto.getFields(), result);
        } else {
            throw new IllegalArgumentException("Unsupported type of the input of marshalling: %s.".formatted(graph.getClass().getName()));
        }
    }

    private void marshalFields(Map<String, Object> fields, Result result) {
        try {
            var xmlWriter = new XmlEventsWriter(XMLOutputFactory.newInstance().createXMLEventWriter(result));

            xmlWriter.writeDocumentStart();

            var rootField = extractRootField(fields);

            writeObject(rootField.getKey(), rootField.getValue(), xmlWriter);

            xmlWriter.writeDocumentEnd();
        } catch (XMLStreamException e) {
            throw new XmlMarshallingException(e.getMessage(), e);
        }
    }

    private Map.Entry<String, Object> extractRootField(Map<String, Object> fields) {
        if (fields.size() > 1) {
            throw new XmlMarshallingException("There are too many root fields, there must be no more than one.");
        }

        var rootField = fields.entrySet().stream()
                .findFirst()
                .orElseThrow(() -> new XmlMarshallingException("No fields to marshal."));

        validateRootFieldName(rootField.getKey());

        return rootField;
    }

    private void validateRootFieldName(String rootFieldName) {
        if (XmlConstants.VALUE_FIELD_NAME.equals(rootFieldName)) {
            throw new XmlMarshallingException("Unable to use `%s` as the root element".formatted(XmlConstants.VALUE_FIELD_NAME));
        }

        if (XmlConstants.ATTRIBUTES_FIELD_NAME.equals(rootFieldName)) {
            throw new XmlMarshallingException("Unable to use `%s` as the root element".formatted(XmlConstants.ATTRIBUTES_FIELD_NAME));
        }
    }

    private void writeObject(String elementName, Object object, XmlEventsWriter xmlWriter) throws XMLStreamException {
        if (object instanceof Map<?, ?> map) {
            writeMap(elementName, map, xmlWriter);
            return;
        }

        if (object instanceof List<?> list) {
            writeList(elementName, list, xmlWriter);
            return;
        }

        writeString(elementName, String.valueOf(object), xmlWriter);
    }

    private void writeMap(String elementName, Map<?, ?> map, XmlEventsWriter xmlWriter) throws XMLStreamException {
        var attributes = getAttributes(map);

        if (map.containsKey(XmlConstants.VALUE_FIELD_NAME)) {
            validateInnerTextMap(map);

            writeInnerTextMap(elementName, attributes, getInnerText(map), xmlWriter);

            return;
        }

        writeElementsMap(elementName, attributes, map, xmlWriter);
    }

    private void validateInnerTextMap(Map<?, ?> map) {
        if (!map.containsKey(XmlConstants.ATTRIBUTES_FIELD_NAME)) {
            throw new XmlMarshallingException(
                    "Maps with `%s` but without `%s` should be replaced with string.".formatted(
                            XmlConstants.VALUE_FIELD_NAME,
                            XmlConstants.ATTRIBUTES_FIELD_NAME
                    )
            );
        }

        if (map.size() > 2) {
            throw new XmlMarshallingException("Maps with `%s` and nested elements are not supported.".formatted(XmlConstants.VALUE_FIELD_NAME));
        }
    }

    private void writeInnerTextMap(String elementName, Map<String, String> attributes, String innerText, XmlEventsWriter xmlWriter) throws XMLStreamException {
        xmlWriter.writeElementStart(elementName, attributes);

        xmlWriter.writeInnerText(innerText);

        xmlWriter.writeElementEnd(elementName);
    }

    private void writeElementsMap(
            String elementName,
            Map<String, String> attributes,
            Map<?, ?> elementsAndAttributes,
            XmlEventsWriter xmlWriter
    ) throws XMLStreamException {
        xmlWriter.writeElementStart(elementName, attributes);

        for (var entry : elementsAndAttributes.entrySet()) {
            if (entry.getKey().equals(XmlConstants.ATTRIBUTES_FIELD_NAME)) {
                continue;
            }

            writeObject(xmlNameToString(entry.getKey()), entry.getValue(), xmlWriter);
        }

        xmlWriter.writeElementEnd(elementName);
    }

    private Map<String, String> getAttributes(Map<?, ?> map) {
        var attributes = map.get(XmlConstants.ATTRIBUTES_FIELD_NAME);

        if (attributes == null) {
            return Map.of();
        }

        if (attributes instanceof Map<?, ?> attributesMap) {
            return attributesMap.entrySet().stream()
                    .collect(Collectors.toMap(entry -> xmlNameToString(entry.getKey()), entry -> String.valueOf(entry.getValue())));
        }

        throw new XmlMarshallingException("Unable to marshal: attributes must be of the Map type.");
    }

    private String getInnerText(Map<?, ?> map) {
        var innerText = map.get(XmlConstants.VALUE_FIELD_NAME);

        if (innerText == null) {
            return "";
        }

        return String.valueOf(innerText);
    }

    private String xmlNameToString(Object name) {
        if (name instanceof String stringName) {
            return stringName;
        }

        throw new IllegalArgumentException("Invalid type of an XML name. Expected: String, got: %s.".formatted(name.getClass().getName()));
    }

    private void writeList(String elementName, List<?> list, XmlEventsWriter xmlWriter) throws XMLStreamException {
        for (var object : list) {
            writeObject(elementName, object, xmlWriter);
        }
    }

    private void writeString(String elementName, String string, XmlEventsWriter xmlWriter) throws XMLStreamException {
        xmlWriter.writeElementStart(elementName, Map.of());

        xmlWriter.writeInnerText(string);

        xmlWriter.writeElementEnd(elementName);
    }
}
