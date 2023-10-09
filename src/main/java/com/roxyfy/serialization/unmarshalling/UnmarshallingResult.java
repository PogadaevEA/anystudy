package com.roxyfy.serialization.unmarshalling;

import com.roxyfy.serialization.XmlConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the logic of XML representation in the form of an object.
 */
public class UnmarshallingResult {

    private final Map<String, Object> resultMap = new LinkedHashMap<>(2);
    private boolean completed = false;

    public void addElement(String name, Object value) {
        checkNotCompleted();

        if (XmlConstants.ATTRIBUTES_FIELD_NAME.equals(name) || XmlConstants.VALUE_FIELD_NAME.equals(name)) {
            throw new IllegalArgumentException("Unable to add an element with `%s` name.".formatted(name));
        }

        if (!resultMap.containsKey(name)) {
            resultMap.put(name, value);
            return;
        }

        var field = resultMap.get(name);
        //noinspection rawtypes
        if (field instanceof List fields) {
            //noinspection unchecked
            fields.add(value);
        } else {
            var fields = new ArrayList<>();
            fields.add(field);
            fields.add(value);
            resultMap.put(name, fields);
        }
    }

    public void addTextPart(String textPart) {
        checkNotCompleted();

        resultMap.merge(XmlConstants.VALUE_FIELD_NAME, textPart, (value1, value2) -> (String) value1 + value2);
    }

    public void setAttributes(Map<String, String> attributes) {
        checkNotCompleted();

        if (resultMap.containsKey(XmlConstants.ATTRIBUTES_FIELD_NAME)) {
            throw new IllegalStateException("Attributes are already set.");
        }

        if (!attributes.isEmpty()) {
            resultMap.put(XmlConstants.ATTRIBUTES_FIELD_NAME, attributes);
        }
    }

    public Object toObject() {
        checkNotCompleted();

        resultMap.compute(XmlConstants.VALUE_FIELD_NAME, (key, value) -> value == null || ((String) value).isBlank() ? null : ((String) value).strip());

        if (resultMap.isEmpty()) {
            return "";
        }

        if (resultMap.containsKey(XmlConstants.VALUE_FIELD_NAME) && resultMap.size() == 1) {
            return resultMap.get(XmlConstants.VALUE_FIELD_NAME);
        }

        completed = true;

        return resultMap;
    }

    private void checkNotCompleted() {
        if (completed) {
            throw new IllegalStateException("Unmarshalling result is already completed.");
        }
    }
}
