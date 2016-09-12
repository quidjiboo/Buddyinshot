package ru.akov.buddyinshot;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

import ru.akov.buddyinshot.Tipes_of_DATA.MyDate_format;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_product extends AppCompatActivity {

    static void choosing_barbershop_product(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load,FragmentManager manager) {

        mDatabase.child("shops").child(shopname_load).child("products").child(myposition).child("workdays").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        /// TODO ДОБАВИТЬ СПИСОК ЗАНЯТЫХ ДНЕЙ , И ТОЛЬКО ПОСЛЕ ЭТОГО ЗАПОЛНЯТЬ КАЛЕНДАРИК! может и в реальном времени потому что при нажатии стоит одиночный листнер
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("уккщк", "getUser:onCancelled", databaseError.toException());
                    }
                });


       /// сохдёте окно календарика!
        Calendarik_creator.create(mDatabase,user,myposition,shopname_load).show(manager, "TAG");



    }

    static void touch_the_product(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load,FragmentManager manager,String tipe_shop) {

        String tipe=tipe_shop;
        switch (tipe){
        case "barbershop": { choosing_barbershop_product(mDatabase,user,myposition,shopname_load,manager);
        break;}

            default:{

                break;
            }
            }





    }

}