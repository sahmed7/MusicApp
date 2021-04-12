package com.musicapp.demo.service;

import com.musicapp.demo.model.Genre;
import com.musicapp.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private GenreRepository genreRepository;


    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

//    public Genre getGenre(Long genreId){
//
//    }
}
