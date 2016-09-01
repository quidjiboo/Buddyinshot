package ru.akov.buddyinshot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_list    {

    MyCallback myCallback;

    void registerCallBack(MyCallback callback){
        this.myCallback = callback;
    }
    public void chek_shop(final DatabaseReference mDatabase, final FirebaseUser user,String string) {
        mDatabase.child("shops").child(string).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Shops shop = dataSnapshot.getValue(Shops.class);
                        if(shop!=null){
                            myCallback.callBackReturn_populateprofile(shop);
                        }

                        if (shop != null && user != null && shop.getadmin().toString().equals(user.getUid())) {
                            myCallback.callBackReturn();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("OSHIBCAK", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
    public void populateProfile(Shops shop, Activity view, ImageView mShopPicPicture,TextView mShop_text, TextView mShop_tipe  ) {
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


}
