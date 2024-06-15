package com.finobank.users.core.service.impl;

import com.finobank.users.core.config.TokenMetaData;
import com.finobank.users.core.domain.User;
import com.finobank.users.core.exception.ApplicationEntityNotFoundException;
import com.finobank.users.core.factory.UserFactory;
import com.finobank.users.core.model.ApiUser;
import com.finobank.users.core.service.UserService;
import com.finobank.users.database.entity.UserEntity;
import com.finobank.users.database.factory.DbUserFactory;
import com.finobank.users.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ApiUser createUser(ApiUser apiUser) {
        User user = User.builder()
                .id(TokenMetaData.getUserId())
                .username(TokenMetaData.getUsername())
                .address(apiUser.getAddress())
                .build();

        UserEntity savedUser = userRepository.save(DbUserFactory.toEntity(user));

        return UserFactory.api(DbUserFactory.fromEntity(savedUser));
    }

    @Override
    public List<ApiUser> getUsers(List<UUID> userIds) {
        Set<UUID> uniqueUserIds = new HashSet<>(userIds);
        uniqueUserIds.add(TokenMetaData.getUserId());

        List<UserEntity> users = userRepository.findAllById(uniqueUserIds);

        if (users.isEmpty()) {
            throw new ApplicationEntityNotFoundException(TokenMetaData.getUserId());
        }

        return users.stream()
                .map(DbUserFactory::fromEntity)
                .map(UserFactory::api)
                .toList();
    }
}
