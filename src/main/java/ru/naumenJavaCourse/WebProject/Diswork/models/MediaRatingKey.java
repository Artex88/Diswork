package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Embeddable
public class MediaRatingKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "media_id")
    private int mediaId;

    public MediaRatingKey() {
    }

    public MediaRatingKey(int userId, int mediaId) {
        this.userId = userId;
        this.mediaId = mediaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaRatingKey that = (MediaRatingKey) o;

        if (userId != that.userId) return false;
        return mediaId == that.mediaId;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + mediaId;
        return result;
    }
}
