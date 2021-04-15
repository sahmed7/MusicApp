package com.musicapp.demo.controller;

import com.musicapp.demo.model.Profile;
import com.musicapp.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profile")
    public ResponseEntity<HashMap> createProfile(@RequestBody Profile profileObject) {
        System.out.println("calling createUserProfile ==>");
        String status = profileService.createProfile(profileObject);
        HashMap message = new HashMap();
        message.put("message", status);
        return new ResponseEntity<HashMap>(message, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public Profile updateProfile(@RequestBody Profile userProfileObject) {
        System.out.println("calling updateUserProfile ==>");
        return profileService.updateProfile(userProfileObject);
    }
}