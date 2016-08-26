package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Loadtest extends AppCompatActivity   {
    private ListView messagesView;
    private  FirebaseListAdapter mAdapter;

    private Boolean Show_modifibutton=false;
    private My_app app;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private  String shopname_load = "noname_default";
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

        //если активити открыть без передачи в неё данных - пропускается пункт передачи названия шопа
        if(getIntent().getStringExtra("shopname")!=null){
        shopname_load = getIntent().getStringExtra("shopname");}
        else{ shopname_load = "noname_default";}





        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//Странная проверка !! продумать
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
    public boolean onCreateOptionsMenu(Menu menu) {
   //
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_shop, menu);
        if(!Show_modifibutton){
        MenuItem item = menu.findItem(R.id.action_modify_shop);
        item.setVisible(false);}


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.action_modify_shop) {
            Intent intent = new Intent(Loadtest.this, Shop_Constructor.class);
            intent.putExtra("shopname",shopname_load);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
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

        app.getmDatabase().child("shops").child(shopname_load).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        shop = dataSnapshot.getValue(Shops.class);
                          Log.w("SHIOP", "Получилаю данные магазина.....");

  //  Log.v("AKOV",app.getauth().getCurrentUser().toString());
                    if(shop!=null){

//проверка на наличие залогиненого пользователя
                        if(app.getauth().getCurrentUser()!=null&&shop.getadmin().toString().equals(app.getauth().getCurrentUser().getUid())){
                            Show_modifibutton=true; }


                        populateProfile();
                        // ...
                        }
                        else{
                        Log.v("AKOV","Нет данных по магазину!!!!!!!");
                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("OSHIBCAK", "getUser:onCancelled", databaseError.toException());
                    }
                });


///ЗАполнение списка товаров
        messagesView = (ListView) findViewById(R.id.listView_products);
        if (app.getmDatabase().child("shops").child(shopname_load).child("products") != null) {
            mAdapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.simple_list_item_2, app.getmDatabase().child("shops").child(shopname_load).child("products")) {
                @Override
                protected void populateView(View view, Product chatMessage, int position) {
                    if (chatMessage != null) {
                        ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getname());
                        ((TextView) view.findViewById(android.R.id.text2)).setText(chatMessage.getprice());
                    }
                }
            };
            messagesView.setAdapter(mAdapter);

        }

    }

    @MainThread
    private void populateProfile() {



                Glide.with(this)
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

                    //TextUtils.isEmpty(shop.gettipe_of_shop()) ? "oops" : shop.gettipe_of_shop());





        }



}
