package com.oocl.training.Util;

import java.util.NoSuchElementException;

public class DataBaseQueryException extends NoSuchElementException {
    public DataBaseQueryException(String message) {
        super(message);
    }
}
