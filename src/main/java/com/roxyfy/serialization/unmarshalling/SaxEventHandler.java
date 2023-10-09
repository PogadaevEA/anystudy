package com.roxyfy.serialization.unmarshalling;

import com.roxyfy.exceptions.XmlUnmarshallingException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * An implementation of {@link ContentHandler} that converts XML into an object.
 * It performs XML event preprocessing and delegates parsing to the {@link ObjectParser} object.
 */
public class SaxEventHandler extends DefaultHandler {

    private String rootTag;
    private final ObjectParser rootParser = new ObjectParser();
    private final Deque<String> path = new LinkedList<>();

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        path.addLast(qualifiedName);

        checkNoNamespace(uri);

        var attributesMap = mapAttributes(attributes);
        if (rootTag == null) {
            rootTag = qualifiedName;
        }

        rootParser.handleElementStart(attributesMap, path);
    }

    private Map<String, String> mapAttributes(Attributes attributes) {
        var attributesMap = new LinkedHashMap<String, String>(attributes.getLength());

        for (int i = 0; i < attributes.getLength(); i++) {
            checkNoNamespace(attributes.getURI(i));
            attributesMap.put(attributes.getQName(i), attributes.getValue(i));
        }

        return attributesMap;
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName) {
        checkNoNamespace(uri);

        rootParser.handleElementEnd(qualifiedName);

        path.removeLast();
    }

    @Override
    public void characters(char[] chars, int start, int length) {
        rootParser.handleInnerText(new String(chars, start, length), path);
    }

    public Map<String, Object> getResult() {
        if (!rootParser.isFinished()) {
            throw new IllegalStateException("Parsing is not finished yet.");
        }
        return Map.of(rootTag, rootParser.getResult());
    }

    private void checkNoNamespace(String uri) {
        if (!uri.isEmpty()) {
            throw new XmlUnmarshallingException("Namespaces are not supported.", path);
        }
    }
}
