package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Loadtest extends AppCompatActivity   {
    private My_app app;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    Shops shop;
    @BindView(R.id.ShopPicPicture)
    ImageView mShopPicPicture;
    @BindView(R.id.Shop_text)
    TextView mShop_text;
    @BindView(R.id.Shop_tipe)
    TextView mShop_tipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  next_scr(getCurrentFocus());

                }
            });
        }
        //тест для гита
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        app = ((My_app) getApplicationContext());
        this.auth=app.getauth();
        this. mAuthListener=app.getmAuthListener();

        getshopInfo();


    }



    @Override
    protected void onStart() {
        super.onStart();

     //   auth.addAuthStateListener(mAuthListener);
    }

    public void next_scr(View view) {

        Intent intent = new Intent(Loadtest.this, Buddylist.class);

        startActivity(intent);


        this.finish();

    }

    @MainThread
    private void getshopInfo() {

        app.getmDatabase().child("shops").child("KmxJEV9s5zfhN0Kz1oJUhQ9O01o1").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        shop = dataSnapshot.getValue(Shops.class);
                        Log.w("SHIOP", "Получил данные магазина");
                        populateProfile();
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("OSHIBCAK", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    @MainThread
    private void populateProfile() {


        if(shop!=null){




                Glide.with(this)
                        .load(shop.getphotourl())
.centerCrop()
                        .into(mShopPicPicture);








            mShop_text.setText(
                    TextUtils.isEmpty(shop.getname()) ? "oops" : shop.getname());
            mShop_tipe.setText(
                    TextUtils.isEmpty(shop.gettipe_of_shop()) ? "oops" : shop.gettipe_of_shop());




        } else {
            Log.v("AKOV","NO SHOPS INFO!!!!!!!");
        }}



}
