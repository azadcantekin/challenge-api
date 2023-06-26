package com.techcareer.challenge.data.enums;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private String type;

    RoleType(String type) {
        this.type = type;
    }
}