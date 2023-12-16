package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;

import java.math.BigDecimal;
import java.util.Optional;


@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByMediaName(String name);

    Optional<Media> findById(int id);

    @Query("select sum(um.grade) from Media join UserMedia um on um.mediaRatingKey.mediaId = :id ")
    Double getTotalNumberOfRatingPointsRating(@Param("id") int id);

    @Query("select count(*) from Media join UserMedia um on um.mediaRatingKey.mediaId = :id where um.grade != 0")
    Double getNumberOfTimesWhenMediaGraded(@Param("id") int id);

    @Modifying
    @Query("update Media set rating= :rating where id=:id")
    void updateMediaByRating(@Param("rating") double rating, @Param("id") int id);
}
