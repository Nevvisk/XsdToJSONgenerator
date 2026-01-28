package com.simmerr.xsdjson.parser.exceptions;

public class XsdLoadException extends IllegalArgumentException {
    public XsdLoadException(String message) {
        super(message);
    }

    public XsdLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
