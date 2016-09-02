package ru.akov.buddyinshot.Tipes_of_DATA;

/**
 * Created by User on 02.09.2016.
 */
public class Product_varibles_barbershop {
    public String tipe;



    public Long start;
    public Long stop;
    public Long clients;

    public Product_varibles_barbershop() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product_varibles_barbershop(String tipe,Long start,Long stop,Long clients) {
        //  this.product = product;
        this.tipe = tipe;
        this.start = start;
        this.stop = stop;
        this.clients = clients;


    }
    public String gettipe() {
        return tipe;
    }
    public Long getstart() {
        return start;
    }

    public Long getstop() {
        return stop;
    }

    public Long getclients() {
        return clients;
    }
    }

