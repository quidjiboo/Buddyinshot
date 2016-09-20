package ru.akov.buddyinshot;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import ru.akov.buddyinshot.On_product_of_barbershop_click.Tourch_product;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_product extends AppCompatActivity {



    static void touch_the_product(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load, FragmentManager manager, String tipe_shop, Tourch_product product_touch_listenr) {

        String tipe=tipe_shop;
        switch (tipe){

        case "barbershop": { product_touch_listenr.choosing_barbershop_product(mDatabase,user,myposition,shopname_load,manager);
        break;}

            default:{

                break;
            }
            }





    }

}