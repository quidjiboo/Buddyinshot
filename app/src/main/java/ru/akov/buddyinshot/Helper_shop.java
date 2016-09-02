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
import ru.akov.buddyinshot.Tipes_of_DATA.Shops;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_shop extends AppCompatActivity {

    static void populateProfile(Shops shop, Activity view, ImageView mShopPicPicture, TextView mShop_text, TextView mShop_tipe  ) {
        Glide.with(view)
                .load(shop.getphotourl())
                .centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mShopPicPicture);

        mShop_text.setText(
                TextUtils.isEmpty(shop.getname()) ? "oops" : shop.getname());
        //Преобразование типа с английского на русскай
        String tipeofshop="noname";
        switch (shop.gettipe_of_shop()) {
            case "barbershop":  tipeofshop = "парикмахерская";
                break;}
        mShop_tipe.setText(tipeofshop);
    }

/// заполнение списка продуктов в зависимости от типа магазина
    static public FirebaseListAdapter chtoto(String t, DatabaseReference mDatabase,String shopname_load,Activity activiti){
        String tipeshop;
    FirebaseListAdapter Xdd;
        tipeshop=t;
if(tipeshop.equals("ваываыва")){
      Xdd = new FirebaseListAdapter<Product>(activiti, Product.class, android.R.layout.simple_list_item_2, mDatabase.child("shops").child(shopname_load).child("products")) {
            @Override
            protected void populateView(View view, Product chatMessage, int position) {
                if (chatMessage != null) {
                    ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getname());
                    ((TextView) view.findViewById(android.R.id.text2)).setText(chatMessage.getprice());
                }
            }
        };}
//Стандартный магазин (сейчас парикмахерская)
else { Xdd = new FirebaseListAdapter<Product>(activiti, Product.class, android.R.layout.simple_list_item_2, mDatabase.child("shops").child(shopname_load).child("products")) {
    @Override
    protected void populateView(View view, Product chatMessage, int position) {
        if (chatMessage != null) {
            ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getname());
            ((TextView) view.findViewById(android.R.id.text2)).setText(chatMessage.getprice());
        }
    }
};}

        return Xdd;
    }



}