package com.musicapp.demo.controller;

import com.musicapp.demo.model.Genre;
import com.musicapp.demo.model.Song;
import com.musicapp.demo.model.User;
import com.musicapp.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class GenreController {
    private GenreService genreService;
    @Autowired
    public void setGenreService(GenreService genreService){this.genreService = genreService;}

    @GetMapping("/hello")
    public String helloWorld(){return "Hello World";}

    @GetMapping("/genres")
    public List<Genre> getGenres(){
      return genreService.getGenres();
    }

    @GetMapping("/genres/{genreId}")
    public Genre getGenre(@PathVariable Long genreId){
        System.out.println("testing getGenre method");
        return genreService.getGenre(genreId);
    }

    //https://localhost:9093/api/genres
    @PostMapping("/genres")
    public Genre createGenre(@RequestBody Genre genreObject){
        System.out.println("Creating Genre");
        return genreService.createGenre(genreObject);
    }

    @PutMapping("/genres/{genreId}")
    public Genre updateGenre(@PathVariable Long genreId, @RequestBody Genre genreObject){
        return genreService.updateGenre(genreId, genreObject);
    }

//    @DeleteMapping("/genres/{genreId}")
//    public String deleteGenre(@PathVariable Long genreId){
//        return genreService.deleteGenre(genreId);
//    }

    @PostMapping("/genres/{genreId}/songs")
    public Song createGenreSong(@PathVariable Long genreId, @RequestBody Song songObject){
        return genreService.createGenreSong(genreId, songObject);
    }

    @PutMapping("/genres/{genreId}/songsList")
    public User addSongsToMyList(@PathVariable Long genreId, @RequestBody HashMap<String, ArrayList<Integer>> songs){
        return genreService.addSongsToMyList(genreId, songs);
    }

    @GetMapping("/genres/{genreId}/songs")
    public List<Song> getGenreSongs(@PathVariable Long genreId){
        return genreService.getGenreSongs(genreId);
    }

    @PutMapping("/genres/{genreId}/songs/{songId}")
    public Song updateGenreSong(@PathVariable Long genreId, @PathVariable Long songId, @RequestBody Song songObject){
        return genreService.updateGenreSong(genreId, songId, songObject);
    }

    @DeleteMapping(path = "/genres/{genreId}/songs/{songId}")  //user is entering/passing the id anyway, use it
    public ResponseEntity<HashMap> deleteGenreSong(@PathVariable Long genreId, @PathVariable Long songId) {
        genreService.deleteGenreSong(genreId, songId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("Status", "Song with id: "+ songId + " was successfully deleted!");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
    }

}
