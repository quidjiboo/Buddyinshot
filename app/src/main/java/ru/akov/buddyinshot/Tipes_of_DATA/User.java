package ru.akov.buddyinshot.Tipes_of_DATA;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String photourl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String photourl) {
        this.username = username;
        this.email = email;
        this.photourl = photourl;
    }
    public String getusername() {
        return username;
    }
    public String getemail() {
        return email;
    }
    public String getphotourl() {
        return photourl;
    }



}