package ru.naumenJavaCourse.WebProject.Diswork.dto;

public class GradingDTO {
    private int grade;

    private int mediaId;

    public GradingDTO() {
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
