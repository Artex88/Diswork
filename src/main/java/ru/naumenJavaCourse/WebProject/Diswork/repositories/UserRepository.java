package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.naumenJavaCourse.WebProject.Diswork.models.User;

import java.time.Period;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserById(int id);
}
