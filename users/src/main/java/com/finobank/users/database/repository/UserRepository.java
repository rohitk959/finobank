package com.finobank.users.database.repository;

import com.finobank.users.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
