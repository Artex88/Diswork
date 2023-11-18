package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;

import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByTagName(String tagName);
}
