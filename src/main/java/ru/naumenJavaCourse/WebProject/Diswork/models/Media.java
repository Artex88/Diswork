package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull
    @NotEmpty( message = "Название не должно быть пустым")
    private String mediaName;
    @Column(name = "description")
    @NotNull
    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    @Column(name = "poster")
    private String posterPath;
    @Column(name = "age_rating")
    @NotNull
    @NotEmpty(message = "Возрастной рейтинг не должен быть пустым")
    private String age_rating;
    @Column(name = "duration")
    @NotNull
    private int duration;
    @Column(name = "episode_count")
    @NotNull
    private int episode_count;

    @ManyToMany
    @NotNull
    @JoinTable(
            name = "media_tag",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @NotNull
    private Type type;

    public Media() {
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

    public String getAge_rating() {
        return age_rating;
    }

    public void setAge_rating(String age_rating) {
        this.age_rating = age_rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
