package ru.akov.buddyinshot;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandr on 09.07.2016.
 */
@IgnoreExtraProperties
public class MyDate_format {

    public String month;
    public String year;
    public String day;

    public MyDate_format() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MyDate_format(String month, String year, String day) {
        this.month = month;
        this.year = year;
        this.day = day;
    }
    public String getmonth() {
        return month;
    }
    public String getyear() {
        return year;
    }
    public String getday() {
        return day;
    }



}