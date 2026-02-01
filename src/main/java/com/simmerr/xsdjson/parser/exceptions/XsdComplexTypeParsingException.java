package com.simmerr.xsdjson.parser.exceptions;

public class XsdComplexTypeParsingException extends RuntimeException {
    public XsdComplexTypeParsingException(String message) {
        super(message);
    }
    public XsdComplexTypeParsingException(String message, Throwable cause) {
      super(message, cause);
    }
}
