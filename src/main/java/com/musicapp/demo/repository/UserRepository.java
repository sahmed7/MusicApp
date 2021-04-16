package com.musicapp.demo.repository;


import com.musicapp.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    boolean existsByEmailAddress(String userEmailAddress);

    User findById(int userId);
    User findUserByEmailAddress(String userEmailAddress);

}
