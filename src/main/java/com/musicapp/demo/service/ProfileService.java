package com.musicapp.demo.service;

import com.musicapp.demo.exception.InformationExistException;
import com.musicapp.demo.model.User;
import com.musicapp.demo.model.Profile;
import com.musicapp.demo.repository.ProfileRepository;
import com.musicapp.demo.repository.UserRepository;
import com.musicapp.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String createProfile(Profile profileObject) {
        System.out.println("service calling createUserProfile ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (userDetails.getUser().getProfile() != null) {
            throw new InformationExistException("user profile is already exists");
        }
        userDetails.getUser().setProfile(profileObject);
        userRepository.save(userDetails.getUser());
        return "profile created successfully";
    }

    public void updateProfile(Profile profileObject) {
        System.out.println("service calling updateUserProfile ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> currentUser = userRepository.findById(userDetails.getUser().getId());
        if (currentUser.isPresent()) {
            Profile profile = currentUser.get().getProfile();
            profile.setFirstName(profileObject.getFirstName());
            profile.setLastName(profileObject.getLastName());
            profile.setDescription(profileObject.getDescription());
            currentUser.get().setProfile(profile);
            profileRepository.save(profile);
            //return profile;
        } else {
            throw new InformationExistException("user profile does not exist for current user");
        }
    }
}