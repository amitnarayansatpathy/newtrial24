package com.example.newtrial24;

public class ResponseWrapper<T> {
    private T data;
    private ErrorResponse Error;

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public ResponseWrapper(ErrorResponse Error) {
        this.Error = Error;
    }

    public T getData() {
        return data;
    }

    public ErrorResponse getError() {
        return Error;
    }

    public boolean hasError() {
        return Error != null;
    }
}