package com.musicapp.demo;

import com.musicapp.demo.model.Song;
import com.musicapp.demo.model.User;
import com.musicapp.demo.repository.SongRepository;
import com.musicapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.zip.DataFormatException;

@SpringBootApplication
public class MusicAppApplication implements CommandLineRunner {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(MusicAppApplication.class, args);

    }
    @Override
    public void run(String...args) throws Exception {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        User user1 = new User(3L, "username2", "shaheer@ahmed.com", "123abc");
        //userRepository.save(user1);

        Song song1 = new Song(1L, "RockingSong1", new Date(year));
        Song song2 = new Song(2L, "RockingSong2", new Date(year));
        Song song3 = new Song(3L, "RockingSong3", new Date(year));
//        songRepository.save(song1);
//        songRepository.save(song2);
        //songRepository.saveAll(Arrays.asList(song1, song2, song3));
        //user1.setSongList(Arrays.asList(song1, song2, song3));
        //user1.getSongList().addAll(Arrays.asList(song1, song2, song3));
        //song1.setUserList(Arrays.asList(user1));
        songRepository.save(song1);
        songRepository.save(song2);
        songRepository.save(song3);
        user1.setSongList(Arrays.asList(song1, song2, song3));
        userRepository.save(user1);

        System.out.println(user1.getSongList());
        System.out.println(user1.getSongList().get(1).getTitle());
    }

}
