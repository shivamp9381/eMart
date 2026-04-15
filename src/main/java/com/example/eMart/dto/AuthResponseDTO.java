package com.example.eMart.dto;

public class AuthResponseDTO {

    private String message;

    public AuthResponseDTO(String message) {
        this.message = message;
    }

    public AuthResponseDTO() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
