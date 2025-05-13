package com.chokchok.chokchokfront.member.exception;

public class LogoutFailedException extends RuntimeException {
    public LogoutFailedException(String message) {
        super(message);
    }
}
