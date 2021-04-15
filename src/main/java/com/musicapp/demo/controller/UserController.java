package com.musicapp.demo.controller;

import com.musicapp.demo.model.Song;
import com.musicapp.demo.model.request.LoginRequest;
import com.musicapp.demo.model.User;
import com.musicapp.demo.model.request.LoginRequest;
import com.musicapp.demo.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/auth/users")
public class UserController {

    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    // http://localhost:9093/auth/users/register
    @PostMapping("/register")
    public User createUser(@RequestBody User userObject){
        System.out.println("Calling createUser ==>");
        return userService.createUser(userObject);
    }

    // http://localhost:9093/auth/users/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("calling loginUser ==>");
        return userService.loginUser(loginRequest);
    }

    // http://localhost:9093/auth/users/changepassword
    @PutMapping("/changepassword")
    public ResponseEntity<HashMap> updatePassword(@RequestBody HashMap<String, String> passwordObj){
        userService.updatePassword(passwordObj);
        HashMap responseMessage = new HashMap();
        responseMessage.put("Status", "Password was successfully updated!");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
    }
}
