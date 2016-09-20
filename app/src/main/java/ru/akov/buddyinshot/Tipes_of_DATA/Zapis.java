package ru.akov.buddyinshot.Tipes_of_DATA;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class Zapis {


    public String name;

    public Boolean cantrue;

    public Zapis() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Zapis(String name,  Boolean cantrue) {
      //  this.product = product;
        this.name = name;

        this.cantrue = cantrue;

    }
    public String getname() {
        return name;
    }

    public Boolean getcantrue() {
        return cantrue;
    }





}