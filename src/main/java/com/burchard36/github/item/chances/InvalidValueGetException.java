package com.burchard36.github.item.chances;


public class InvalidValueGetException extends Exception {
    public InvalidValueGetException(final String msg) {
        super(msg);
        this.printStackTrace();
    }
}
