package com.musicapp.demo.controller;

import com.musicapp.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GenreController {
    private GenreService genreService;
    @Autowired
    public void setGenreService(GenreService genreService){this.genreService = genreService;}

    @GetMapping("/hello")
    public String helloWorld(){return "Hello World";}




}
