package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;

import java.util.Optional;
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findByTypeName(String tagName);
}
