package com.finobank.payments.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatus {
    BLOCKED("BLOCKED"),
    EXECUTED("EXECUTED");

    private final String value;

    @JsonCreator
    public static PaymentStatus fromValue(String value) {
        for (PaymentStatus b : PaymentStatus.values()) {
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
