package com.musicapp.demo.repository;

import com.musicapp.demo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository <Genre, Long> {

    Genre findByName (String genreName);

    // find by user id and category then return the category
    Genre findByIdAndUserId(Long categoryId, Long userId);

    // find category by userId and by categoryName
    Genre findByUserIdAndName(Long userId, String categoryName);


    List<Genre> findByUserId(Long userId);

}
