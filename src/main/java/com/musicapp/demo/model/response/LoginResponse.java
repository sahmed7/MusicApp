package com.musicapp.demo.model.response;

// pending user credential authentication passing, this class will send back the JWT Token

public class LoginResponse {

    private String JWT;

    public LoginResponse(String JWT) {
        this.JWT = JWT;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }
}

