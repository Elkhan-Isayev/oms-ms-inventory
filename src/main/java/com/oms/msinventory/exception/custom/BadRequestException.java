package com.oms.msinventory.exception.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
