package com.chokchok.chokchokfront.common;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String message) {
        super(message);
    }
}
