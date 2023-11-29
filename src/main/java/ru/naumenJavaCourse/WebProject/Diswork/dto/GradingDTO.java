package ru.naumenJavaCourse.WebProject.Diswork.dto;

public class GradingDTO {
    private String grade;

    private String mediaId;

    public GradingDTO() {
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
