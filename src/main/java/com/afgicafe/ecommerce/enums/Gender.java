package com.afgicafe.ecommerce.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;


    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
