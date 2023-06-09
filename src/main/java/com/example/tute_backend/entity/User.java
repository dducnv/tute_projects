package com.example.tute_backend.entity;

import com.example.tute_backend.entity.my_enum.AuthProvider;
import com.example.tute_backend.entity.my_enum.UserStatus;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "avatar")
    private String avatar;
    @Column(name="full_name",nullable = false)
    private String name;
    @Column(name="username",unique = true,nullable = false)
    @JsonIgnore
    private String username;
    @JsonIgnore
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @Column(name = "provider")
    private AuthProvider provider; // enum
    @JsonIgnore
    @Column(name = "provider_id")
    private String providerId;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @CreationTimestamp
    @Column(name="created_at")
    @JsonIgnore
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name="updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;
    private UserStatus status;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
}
