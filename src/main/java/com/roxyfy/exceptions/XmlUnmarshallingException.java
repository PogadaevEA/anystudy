package com.roxyfy.exceptions;

import org.springframework.oxm.MarshallingException;

import java.util.Deque;
import java.util.stream.Collectors;

/**
 * Exception class to use when input is valid XML but violates internal constraints.
 */
public class XmlUnmarshallingException extends MarshallingException {

    public XmlUnmarshallingException(String message, Deque<String> path) {
        super("%s Path: %s.".formatted(message, pathToString(path)));
    }

    private static String pathToString(Deque<String> path) {
        return path.stream()
                .map(pathElement -> "<" + pathElement + ">")
                .collect(Collectors.joining());
    }
}
