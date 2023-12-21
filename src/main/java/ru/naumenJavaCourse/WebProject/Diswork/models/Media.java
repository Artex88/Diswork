package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Validated
@Table(name = "media")
public class Media {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 64, message = "Название произведения не должно быть пустым и больше 32 символов")
    @NotEmpty(message = "Название не может быть пустым")
    private String mediaName;
    @Column(name = "description")
    @NotNull
    @NotEmpty(message = "Описание не может быть пустым")
    @Size(max = 512, message = "Описанин произведения не должно превышать 512 символов")
    private String description;

    @Column(name = "poster")
    private String posterPath;
    @Column(name = "age_rating")
    @Size(min = 1, message = "Возрастной рейтинг не должнен быть пустым и больше 8 символов")
    private String ageRating;
    @Column(name = "duration")
    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Некорректно задано число ")
    private int duration;
    @Column(name = "episode_count")
    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Некорректно задано число ")
    private int episodeCount;

    @Column(name = "year_of_release")
    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Некорректно задано число ")
    private int yearOfRelease;

    @Column(name = "rating")
    @NotNull
    private double rating;

    @Column(name = "release_period")
    private String releasePeriod;

    @Column(name = "episode_duration")
    private String episodeDuration;

    @ManyToMany()
    @JoinTable(
            name = "media_tag",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "media", orphanRemoval = true)
    private Set<UserMedia> mediaUser = new HashSet<>();

    @OneToMany(mappedBy = "media", orphanRemoval = true)
    private List<Comment> commentList;

    public Media() {
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Set<UserMedia> getMediaUser() {
        return mediaUser;
    }

    public void setMediaUser(Set<UserMedia> ratings) {
        this.mediaUser = ratings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleasePeriod() {
        return releasePeriod;
    }

    public void setReleasePeriod(String releasePeriod) {
        this.releasePeriod = releasePeriod;
    }

    public String getEpisodeDuration() {
        return episodeDuration;
    }

    public void setEpisodeDuration(String episodeDuration) {
        this.episodeDuration = episodeDuration;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
