package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.stereotype.Service;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserValidateService {

    private final UserRepository userRepository;

    public UserValidateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByLogin(String login){
        Optional<User> user = userRepository.findByLogin(login);
        return user;
    }
}
