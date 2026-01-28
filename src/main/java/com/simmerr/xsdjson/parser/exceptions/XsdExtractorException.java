package com.simmerr.xsdjson.parser.exceptions;

public class XsdExtractorException extends IllegalArgumentException{

    public XsdExtractorException(String message) {
        super(message);
    }

    public XsdExtractorException(String message, Throwable cause) {
        super(message, cause);
    }
}
