package com.example.socio;

/**
 * Created by shiva on 31-01-2018.
 */

public class User {
    String Displayname;


    String Email;
    long createdAt;

    public User(){};
    public User(String Displayname, String email, long createdAt){
        this.Displayname=Displayname;
        this.Email=email;
        this.createdAt=createdAt;
    }


    public String getDisplayname() {
        return Displayname;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

}