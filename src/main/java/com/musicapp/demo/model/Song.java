package com.musicapp.demo.model;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String title;
    @Column
    private Date year;

    public Song() {
    }

    public Song(Long id, String title, Date year) {
        Id = id;
        this.title = title;
        this.year = year;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
