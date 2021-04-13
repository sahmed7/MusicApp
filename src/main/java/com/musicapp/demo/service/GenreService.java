package com.musicapp.demo.service;

import com.musicapp.demo.exception.InformationExistException;
import com.musicapp.demo.exception.InformationNotFoundException;
import com.musicapp.demo.model.Genre;
import com.musicapp.demo.model.Song;
import com.musicapp.demo.model.User;
import com.musicapp.demo.repository.GenreRepository;
import com.musicapp.demo.repository.SongRepository;
import com.musicapp.demo.security.MyUserDetails;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GenreService {
    private GenreRepository genreRepository;
    private SongRepository songRepository;

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

    public Genre updateGenre(Long genreId, Genre genreObject){
        System.out.println("service calling updateGenre");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());

        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            genre.setName(genreObject.getName());
            genre.setDescription(genreObject.getDescription());
            genre.setUser(genreObject.getUser());
            return genreRepository.save(genre);
        }
    }

    public String deleteGenre(Long genreId){
        System.out.println("service calling deleteGenre");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            genreRepository.deleteById(genreId);
            return "genre with id " + genreId + " has been successfully deleted";
        }
    }

    public String createGenreSong(Long genreId, Map<String, String> songObject){
        System.out.println("service calling createGenreSong");
        //MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        System.out.println(songObject.get("title"));
        System.out.println(songObject.get("artistFullName"));
        System.out.println(songObject.get("year"));
        System.out.println(songObject.get("userId"));

//        if(genre == null){
//            throw new InformationNotFoundException("genre with id " + genreId + " not belongs to this user or genre doesn't exist");
//        }
        //Song song = songRepository.findByTitleAndUser(songObject.getTitle(), userDetails.getUser().getId());
//        List<Song> userSongs = userDetails.getUser().getSongList();
//        if(userSongs != null){
////            userSongs.stream().forEach(sg -> {
////                if(sg.getTitle().equals(songObject.getTitle())){
////                    throw new InformationExistException("song with title " + sg.getTitle() + " already exists");
////                }
////            });
//            }
        //songObject.set
//        List<User> updatingUser = new ArrayList();
//        updatingUser.add(userDetails.getUser());
//        songObject.setUserList(updatingUser);
//        songObject.setGenre(genre);
//        songObject.setTitle(songObject.getTitle());
//        songObject.setYear(songObject.getYear());
//        songObject.setArtistFullName(songObject.getArtistFullName());
        return "Hello";
//        return songRepository.save(songObject);

        //title
        //artist full name
        //year
    }

}
