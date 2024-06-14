package com.finobank.accounts.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountStatus {
    ENABLED("ENABLED"),
    BLOCKED("BLOCKED");

    private final String value;

    @JsonCreator
    public static AccountStatus fromValue(String value) {
        for (AccountStatus b : AccountStatus.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
