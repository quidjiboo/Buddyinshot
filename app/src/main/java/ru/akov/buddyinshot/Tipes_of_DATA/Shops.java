package ru.akov.buddyinshot.Tipes_of_DATA;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class Shops {

    public String admin;
    public String name;
    public String tipe_of_shop;
    public String photourl;

    public Shops() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Shops(String name, String tipe_of_shop, String photourl,String admin) {
      //  this.product = product;
        this.name = name;
        this.tipe_of_shop = tipe_of_shop;
        this.photourl = photourl;
        this.admin = admin;
    }
    public String getname() {
        return name;
    }
    public String gettipe_of_shop() {
        return tipe_of_shop;
    }
    public String getphotourl() {
        return photourl;
    }
    public String getadmin() {
        return admin;
    }




}