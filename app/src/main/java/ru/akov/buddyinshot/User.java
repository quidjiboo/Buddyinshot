package ru.akov.buddyinshot;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    String uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }
    public String getName() {
        return username;
    }
    public String getMail() {
        return email;
    }
    public String getUid() {
        return uid;
    }



}