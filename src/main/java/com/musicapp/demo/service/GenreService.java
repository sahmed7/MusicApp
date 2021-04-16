package com.musicapp.demo.service;

import com.musicapp.demo.exception.InformationExistException;
import com.musicapp.demo.exception.InformationNotFoundException;
import com.musicapp.demo.model.Genre;
import com.musicapp.demo.model.Song;
import com.musicapp.demo.model.User;
import com.musicapp.demo.repository.GenreRepository;
import com.musicapp.demo.repository.SongRepository;
import com.musicapp.demo.repository.UserRepository;
import com.musicapp.demo.security.MyUserDetails;
import javassist.NotFoundException;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private GenreRepository genreRepository;
    private SongRepository songRepository;
    private UserRepository userRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Autowired
    public void setSongRepository(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){ this.userRepository = userRepository; }

    //Getting list of genres of current user
    public List<Genre> getGenres(){
        System.out.println("service calling getGenres");
        MyUserDetails userDetails = gettingUserDetails();
        List<Genre> genres = genreRepository.findByUserId(userDetails.getUser().getId());
        if(genres.isEmpty()){
            throw new InformationNotFoundException("No genres found for that user id " + userDetails.getUser().getId());
        }else {
            return genres;
        }
    }

    //Getting single genres with given Id of current user
    public Genre getGenre(Long genreId){
        System.out.println("service calling getGenre");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            return genre;
        }
    }

    //Creating a new genre
    public Genre createGenre(Genre genreObject){
        System.out.println("Service calling createGenre");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByUserIdAndName(userDetails.getUser().getId(), genreObject.getName());
        if(genre != null){
            throw new InformationExistException("genre with name " + genre.getName() + " already exist");
        }else{
            genreObject.setUser(userDetails.getUser());
            return genreRepository.save(genreObject);
        }
    }

    //Updating existing genre
    public Genre updateGenre(Long genreId, Genre genreObject){
        System.out.println("service calling updateGenre");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());

        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            genre.setName(genreObject.getName());
            genre.setDescription(genreObject.getDescription());
            return genreRepository.save(genre);
        }
    }

    //Deleting a genre belong to current user
    public String deleteGenre(Long genreId){
        System.out.println("service calling deleteGenre");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        if(genre == null){
            throw new InformationNotFoundException("genre with id " + genreId + " not found");
        }else{
            genreRepository.deleteById(genreId);
            return "genre with id " + genreId + " has been successfully deleted";
        }
    }

    //Creating a song of a given genre
    public Song createGenreSong(Long genreId, Song songObject) {
        System.out.println("Service calling createGenreSong");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        Song song = songRepository.findByTitle(songObject.getTitle());
        if(genre == null){
            throw new InformationNotFoundException("You cannot create a song for this genre. Genre with id "
                    + genreId + " does not belong to you or the genre doesn't exist");
        } else if(song != null){
            throw new InformationExistException("Song with title " + songObject.getTitle() + " already exists!");
        }
        songObject.setGenre(genre);
        songRepository.save(songObject);
        return songRepository.save(songObject);
    }

    //Associating a list of songs to a user
    public User addSongsToMyList(Long genreId, HashMap<String, ArrayList<Integer>> songs) {
        System.out.println("service calling addSongsToMyList");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        ArrayList<Integer> songIds = songs.get("songs");

        User user = userRepository.findById(userDetails.getUser().getId()).get();

        if (genre == null) {
            throw new InformationNotFoundException("Genre with genre id: "
                    + genreId + " does not belong to you or the genre doesn't exist");
        }
            if (songIds.isEmpty()) {
                throw new InformationNotFoundException("No songs exist!");
            }
            for (int songId : songIds) {
                if (!songRepository.existsById((long) songId)) {
                    throw new InformationNotFoundException("Song " + songId + " not found!");
                } else if (user.getSongs().contains(songRepository.findById(songId))) {
                    throw new InformationExistException("Song " + songId + " already exists in your list!");
                }
                user.addSongs(songRepository.findById(songId));
            }
            return userRepository.save(user);
    }

    //Getting all songs belonging to a given genre and current user
    public List<Song> getGenreSongs(Long genreId){
        System.out.println("service calling getGenreSongs");
        MyUserDetails userDetails = gettingUserDetails();
        User user = userRepository.findById(userDetails.getUser().getId()).get();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());

        if(genre == null){
            throw new InformationNotFoundException("Genre with id " + genreId + " does not belongs to you or genre does not exist");
        }
        List<Song> songList = songRepository.findByGenreId(genreId)
                .stream().filter(song -> song.getUsers().stream().findFirst().isPresent()).collect(Collectors.toList());

        if(songList.isEmpty()){
            throw new InformationExistException("Genre or user doesnt have any song");
        }
        return songList;
    }

    //Updating song belonging a given genre and current user
    public Song updateGenreSong(Long genreId, Long songId, Song songObject){
        System.out.println("service calling getGenreSongs");
        MyUserDetails userDetails = gettingUserDetails();
        User user = userRepository.findById(userDetails.getUser().getId()).get();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        Optional<Song> song = songRepository.findById(songId);
        if(genre == null){
            throw new InformationNotFoundException("Genre with id " + genreId + " not belongs to this user or genre doesn't exist");
        }
        if(song.isEmpty()){
            throw new InformationExistException("Song with id " + songId + " doesn't exist");
        } else if(song.get().getTitle().equalsIgnoreCase(songObject.getTitle()) &&
                song.get().getArtistFullName().equalsIgnoreCase(songObject.getArtistFullName()) &&
                song.get().getYear().equals(songObject.getYear())) {
            throw new InformationExistException("This song with title, artist, and year already exists! You must enter new information to update.");
        }
        song.get().setTitle(songObject.getTitle());
        song.get().setArtistFullName(songObject.getArtistFullName());
        song.get().setYear(songObject.getYear());
        songRepository.save(song.get());
        return song.get();
    }

    //Deleting a song belonging to a given genre and current user
    public void deleteGenreSong(Long genreId, Long songId)  {
        System.out.println("service calling deleteGenreSong ==>");
        MyUserDetails userDetails = gettingUserDetails();
        Genre genre = genreRepository.findByIdAndUserId(genreId, userDetails.getUser().getId());
        if (genre == null) {
            throw new InformationNotFoundException("genre with id " + genreId +
                    " not belongs to this user or genre does not exist");
        }
        Optional<Song> song = songRepository.findByGenreId(
                genreId).stream().filter(p -> p.getId().equals(songId)).findFirst();
        if (!song.isPresent()) {
            throw new InformationNotFoundException("song with id " + songId +
                    " not belongs to this user or song does not exist");
        }
        for(User user : song.get().getUsers()){
            song.get().deleteUsers(user);
            songRepository.deleteById(song.get().getId());
        }
    }

    //Get all the songs in every genre
    public List<Song> getAllSongs(){
        System.out.println("Service calling getAllSongs");
        List<Song> songList = songRepository.findAll();
        if(songList.isEmpty()){
            throw new InformationNotFoundException("there is no song to show");
        }

        return songList;
    }
    // Helper method getting userDetails
    public MyUserDetails gettingUserDetails(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails;
    }

}
