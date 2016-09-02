package ru.akov.buddyinshot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import ru.akov.buddyinshot.Tipes_of_DATA.Product;
import ru.akov.buddyinshot.Tipes_of_DATA.Product_varibles_barbershop;
import ru.akov.buddyinshot.Tipes_of_DATA.Product_varibles_default;
import ru.akov.buddyinshot.Tipes_of_DATA.Shops;

/**
 * Created by User on 01.09.2016.
 */
public class Add_product_Helper extends AppCompatActivity {
//
    static Object getDefaultProduct(String tipe_of_shop) {
        Object x1;
        if(tipe_of_shop.equals("barbershop")){
            x1 = new Product("default","0.0","https://firebasestorage.googleapis.com/v0/b/test-base-soc-net.appspot.com/o/shopping-paper-bag-outline_318-39786.png?alt=media&token=93a2373e-1336-4fbe-9268-924db09e4fb9");
             }
        else {x1 = new Object();}

    return x1;}


    static Object getDefaultProduct_varibles_default(String tipe_of_shop) {
        Object x1;
        if(tipe_of_shop.equals("barbershop")){

            x1 =  new Product_varibles_barbershop("default",8L,16L,4L);
        }
        else {x1 = new Object();}

        return x1;}


}