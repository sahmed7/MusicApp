package com.musicapp.demo.repository;

import com.musicapp.demo.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository <Song, Long> {


    Song findByTitle(String songTitle);
    Song findById(long songId);

    List<Song> findByGenreId(Long songId);

    //Song findByTitleAndUserId(String songTitle, List userList);

}
