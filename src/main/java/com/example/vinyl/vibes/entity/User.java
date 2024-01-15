package com.example.vinyl.vibes.entity;

import com.example.vinyl.vibes.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .admin(this.admin)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }

    public User update(UserDTO userDTO) {
        if (userDTO.getFirstName() != null && !userDTO.getFirstName().equals(this.firstName)) {
            this.firstName = userDTO.getFirstName();
        }
        if (userDTO.getLastName() != null && !userDTO.getLastName().equals(this.getLastName())) {
            this.lastName = userDTO.getLastName();
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().equals(this.getPassword())) {
            this.password = userDTO.getPassword();
        }
        return this;
    }

    @PrePersist
    private void prePersist() {
        LocalDateTime date = LocalDateTime.now();
        this.createdAt = date;
        this.updatedAt = date;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
