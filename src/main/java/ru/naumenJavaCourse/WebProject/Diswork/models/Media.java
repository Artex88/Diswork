package ru.naumenJavaCourse.WebProject.Diswork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "description")
    @NotNull
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
}
