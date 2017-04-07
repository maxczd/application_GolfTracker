package com.cazade.golf.projetgolf;

/**
 * Created by bapti on 07/04/2017.
 */

public class User {

    private String email;
    private String password;
    private String pseudo;
    private float handicap;
    private String photo;

    public User(String email, String password, String pseudo){
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
    }

    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getPseudo(){
        return this.pseudo;
    }
}
