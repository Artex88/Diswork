package ru.naumenJavaCourse.WebProject.Diswork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;
import ru.naumenJavaCourse.WebProject.Diswork.repositories.UserRepository;

import javax.swing.plaf.PanelUI;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id){
        Optional<User> user = userRepository.findUserById(id);
        return user.orElse(null);
    }
    public User findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
