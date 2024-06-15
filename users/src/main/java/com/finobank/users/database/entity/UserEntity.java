package com.finobank.users.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "T_USER")
public class UserEntity {
    @Id
    private UUID id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "ADDRESS")
    private String address;
}
