package com.finobank.users.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
public class User {
    private UUID id;
    private String username;
    private String address;
}
