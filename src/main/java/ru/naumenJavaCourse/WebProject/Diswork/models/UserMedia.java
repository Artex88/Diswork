package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;

@Entity
public class UserMedia {
    @EmbeddedId
    private MediaRatingKey mediaRatingKey = new MediaRatingKey();

    @ManyToOne()
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private Media media;

    private int grade;

    public UserMedia() {
    }

    public MediaRatingKey getMediaRatingKey() {
        return mediaRatingKey;
    }

    public void setMediaRatingKey(MediaRatingKey mediaRatingKey) {
        this.mediaRatingKey = mediaRatingKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
