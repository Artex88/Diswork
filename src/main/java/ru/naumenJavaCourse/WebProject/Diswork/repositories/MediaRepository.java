package ru.naumenJavaCourse.WebProject.Diswork.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import ru.naumenJavaCourse.WebProject.Diswork.models.Media;
import ru.naumenJavaCourse.WebProject.Diswork.models.Status;
import ru.naumenJavaCourse.WebProject.Diswork.models.Tag;
import ru.naumenJavaCourse.WebProject.Diswork.models.Type;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByMediaName(String name);

    Optional<Media> findById(int id);

    void removeById(@Param("id") int id);
    @Query("select sum(um.grade) from Media join UserMedia um on um.mediaRatingKey.mediaId = :id ")
    BigDecimal getTotalNumberOfRatingPointsRating(@Param("id") int id);

    @Query("select count(*) from Media join UserMedia um on um.mediaRatingKey.mediaId = :id where um.grade != 0")
    BigDecimal getNumberOfTimesWhenMediaGraded(@Param("id") int id);

    @Modifying
    @Query("update Media set rating= :rating where id= :id")
    void updateMediaByRating(@Param("rating") BigDecimal rating, @Param("id") int id);

    @Query("select m from Media as m where" +
            "(:type is null or m.type = :type) and" +
            "(:status is null or m.status = :status)  and" +
            "(:releasePeriod is null or m.releasePeriod = :releasePeriod) and" +
            "(:episodeDuration is null or m.episodeDuration = :episodeDuration) and" +
            " :tagsSize = (select COUNT(t) from m.tags as t where t in :tags)")
    List<Media> filter(@Param("type") Type type,
                           @Param("status")  Status status,
                           @Param("releasePeriod") String releasePeriod,
                           @Param("episodeDuration") String episodeDuration,
                           @Param("tags") Set<Tag> tags, int tagsSize);

    // Методы для тестирования

    List<Media> findByType(Type type);

    List<Media> findByStatus (Status status);

    List<Media> findByReleasePeriod (String releasePeriod);

    List<Media> findByEpisodeDuration (String episodeCount);
    @Query("select m FROM Media as m where :tagSize = (select count(t) from m.tags as t where t in :tags) ")
    List<Media> findByTags (Set<Tag> tags, int tagSize);

}
