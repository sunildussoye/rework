package com.sunil.munrotop.model;

public class ClientResponse {
    private String message;

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public ClientResponse withMessage(String message) {

        this.message = message;
        return this;
    }
}
