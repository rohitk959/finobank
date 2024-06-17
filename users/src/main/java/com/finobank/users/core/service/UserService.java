package com.finobank.users.core.service;

import com.finobank.users.core.model.ApiUser;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiUser createUser(ApiUser apiUser);

    List<ApiUser> getUsers(List<UUID> userIds);

    ApiUser updateUser(ApiUser apiUser);
}
