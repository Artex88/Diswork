package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;

import java.util.Optional;


@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByMediaName(String name);

    Optional<Media> findById(int id);
}
