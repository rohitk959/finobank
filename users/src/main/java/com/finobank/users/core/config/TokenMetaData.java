package com.finobank.users.core.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenMetaData {
    private static final ThreadLocal<UUID> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> username = new ThreadLocal<>();

    public static UUID getUserId() {
        return userId.get();
    }

    public static void setUserId(UUID tId) {
        userId.set(tId);
    }

    public static String getUsername() {
        return username.get();
    }

    public static void setUsername(String uname) {
        username.set(uname);
    }

    public static void clear() {
        userId.remove();
        username.remove();
    }
}
