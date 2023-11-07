package ru.naumenJavaCourse.WebProject.Diswork.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class UserDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 30 символов длиной")
    private String username;
    @NotEmpty(message = "Пароль не должнем быть пустым")
    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 18 символов длиной")
    private String password;

    public UserDTO() {
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
}
