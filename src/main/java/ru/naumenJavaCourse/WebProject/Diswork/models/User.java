package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @NotNull
    @Size(min = 5, max = 32, message = "Имя должно быть от 5 до 32 символов длиной")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "Пароль не должен быть пустым")
    @NotNull
    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 18 символов длиной")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @NotEmpty
    @NotNull
    private String role;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "email")
    @NotEmpty(message = "Почта не может быть пустой")
    @NotNull()
    @Email
    private String email;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<UserMedia> userMedia = new HashSet<>();

    public User() {
    }

    public Set<UserMedia> getUserMedia() {
        return userMedia;
    }

    public void setUserMedia(Set<UserMedia> ratings) {
        this.userMedia = ratings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
