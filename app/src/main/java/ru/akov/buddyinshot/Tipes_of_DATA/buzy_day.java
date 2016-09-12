package ru.akov.buddyinshot.Tipes_of_DATA;

import java.util.Date;

/**
 * Created by User on 12.09.2016.
 */
public class Buzy_day {


    Date data;
    int buzy_number;
    public   Buzy_day(Date data21,int num)
    {
        data=data21;
        buzy_number=num;
    }
    public Date getData() {
        return data;
    }

    public int getBuzy_number() {
        return buzy_number;
    }
}
