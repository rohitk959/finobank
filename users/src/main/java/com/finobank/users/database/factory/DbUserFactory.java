package com.finobank.users.database.factory;

import com.finobank.users.core.domain.User;
import com.finobank.users.database.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbUserFactory {
    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .build();
    }

    public static User fromEntity(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .build();
    }
}
