package ru.akov.buddyinshot;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class Product {


    public String name;
    public String price;
    public String photourl;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product(String name, String price, String photourl) {
      //  this.product = product;
        this.name = name;
        this.price = price;
        this.photourl = photourl;

    }
    public String getname() {
        return name;
    }
    public String getprice() {
        return price;
    }
    public String getphotourl() {
        return photourl;
    }





}