package org.github.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.core.Response;

public class ErrorResponse {

    @JsonProperty("status")
    private Response.Status status;

    @JsonProperty("message")
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Response.Status status, String message) {
        this.status = status;
        this.message = message;
    }

}