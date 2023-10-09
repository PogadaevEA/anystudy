package com.roxyfy.exceptions;

import org.springframework.oxm.MarshallingException;

/**
 * Exception class to use when DTO to marshal violates internal constraints.
 */
public class XmlMarshallingException extends MarshallingException {

    public XmlMarshallingException(String msg) {
        super(msg);
    }

    public XmlMarshallingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
