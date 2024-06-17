package com.finobank.users.core.controller;

import com.finobank.users.core.api.UsersApi;
import com.finobank.users.core.model.ApiUser;
import com.finobank.users.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class UserController implements UsersApi {
    private final UserService userService;

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<ApiUser> createUser(ApiUser apiUser) {
        ApiUser user = userService.createUser(apiUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<List<ApiUser>> getUsers(List<UUID> userIds) {
        List<ApiUser> users = userService.getUsers(userIds);
        return ResponseEntity.ok(users);
    }

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<ApiUser> updateUser(ApiUser apiUser) {
        ApiUser user = userService.updateUser(apiUser);
        return ResponseEntity.ok(user);
    }
}
