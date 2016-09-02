package ru.akov.buddyinshot.Tipes_of_DATA;

/**
 * Created by User on 02.09.2016.
 */
public class Product_varibles_default {
    public String tipe;



    public Product_varibles_default() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product_varibles_default(String tipe) {
        //  this.product = product;
        this.tipe = tipe;


    }
    public String gettipe() {
        return tipe;
    }

    }

