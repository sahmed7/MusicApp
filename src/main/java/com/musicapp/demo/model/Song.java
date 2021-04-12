package com.musicapp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private String artistFullName;
    @Column
    private Date year;

    //------- Many to One connection to genre table
    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private Genre genre;
    //---------Many to many connection to user table
    @ManyToMany
    @JoinTable(
            name = "song_user",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> userSet;

    public Song() {
    }

    public Song(Long id, String title, Date year) {
        Id = id;
        this.title = title;
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public String getArtistFullName() {
        return artistFullName;
    }

    public void setArtistFullName(String artistFullName) {
        this.artistFullName = artistFullName;
    }
}
