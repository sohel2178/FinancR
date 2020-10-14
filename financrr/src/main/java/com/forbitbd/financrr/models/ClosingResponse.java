package com.forbitbd.financrr.models;

import java.io.Serializable;

public class ClosingResponse implements Serializable {

    private String title;
    private String message;

    public ClosingResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
