package com.musicapp.demo.model.request;

// user can log in by providing username and password.
// the username will be email as that is the unique identifier for logins

public class LoginRequest {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}