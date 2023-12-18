package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;

import java.util.Optional;
@Service
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByStatusName(String name);

    Optional<Status> findById(int id);
    void removeById(@Param("id") int id);
}
