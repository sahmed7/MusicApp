package com.musicapp.demo.service;

import com.musicapp.demo.exception.InformationExistException;
import com.musicapp.demo.exception.InformationNotFoundException;
import com.musicapp.demo.model.Genre;
import com.musicapp.demo.repository.GenreRepository;
import com.musicapp.demo.security.MyUserDetails;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private GenreRepository genreRepository;


    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres(){
        System.out.println("service calling getGenres");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Genre> genres = genreRepository.findByUserId(userDetails.getUser().getId());
        if(genres.isEmpty()){
            throw new InformationNotFoundException("No genres found for that user id " + userDetails.getUser().getId());
        }else {
            return genres;
        }
    }

    public Genre getGenre(Long genreId){
        System.out.println("service calling getGenre");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            return genre;
        }
    }

    public Genre createGenre(Genre genreObject){
        System.out.println("Service calling createGenre");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Genre genre = genreRepository.findByUserIdAndName(userDetails.getUser().getId(), genreObject.getName());
        if(genre != null){
            throw new InformationExistException("genre with name " + genre.getName() + " already exist");
        }else{
            genreObject.setUser(userDetails.getUser());
            return genreRepository.save(genreObject);
        }
    }
}
