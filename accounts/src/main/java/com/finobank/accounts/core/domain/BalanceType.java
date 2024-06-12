package com.finobank.accounts.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BalanceType {
    AVAILABLE("AVAILABLE"),
    END_OF_DAY("END_OF_DAY");

    private final String value;

    @JsonCreator
    public static BalanceType fromValue(String value) {
        for (BalanceType b : BalanceType.values()) {
            if (b.value.equals(value)) {
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
