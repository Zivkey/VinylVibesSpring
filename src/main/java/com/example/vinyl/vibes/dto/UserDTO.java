package com.example.vinyl.vibes.dto;

import com.example.vinyl.vibes.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String jwt;

    public User toEntity() {
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .password(this.password)
                .email(this.email)
                .admin(this.admin)
                .build();
    }
}
