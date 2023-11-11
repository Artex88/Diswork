package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;


@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
}
