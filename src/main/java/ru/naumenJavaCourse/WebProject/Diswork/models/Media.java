package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
    private String mediaName;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    @NotNull
    private String type;
    @Column(name = "age_rating")
    private String age_rating;
    @Column(name = "duration")
    private Time duration;
    @Column(name = "episode_count")
    private int episode_count;

    @ManyToMany
    @JoinTable(
            name = "media_tag",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Media() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge_rating() {
        return age_rating;
    }

    public void setAge_rating(String age_rating) {
        this.age_rating = age_rating;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
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
