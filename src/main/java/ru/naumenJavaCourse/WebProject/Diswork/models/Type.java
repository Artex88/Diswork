package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_name")
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 32, message = "Название тэга не должно быть пустым и больше 32 символов")
    private String typeName;

    @Column(name = "type_description")
    @NotNull
    @NotEmpty
    @Size(max = 255, message = "Описание не может быть длинее 255 символов")
    private String description;

    @OneToMany(mappedBy = "type")
    private List<Media> mediaList;

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
