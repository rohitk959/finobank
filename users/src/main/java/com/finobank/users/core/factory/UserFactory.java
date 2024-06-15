package com.finobank.users.core.factory;

import com.finobank.users.core.domain.User;
import com.finobank.users.core.model.ApiUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFactory {
    public static User core(ApiUser user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .build();
    }

    public static ApiUser api(User user) {
        return ApiUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .build();
    }
}
