package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.akov.buddyinshot.Tipes_of_DATA.Shops;
import ru.akov.buddyinshot.Tipes_of_DATA.User;


public class Loadtest extends AppCompatActivity  implements MyCallback  {

    private AdapterView.OnItemClickListener mylistner;


    private Helper_Listers helper_Db_listenr;
    private ValueEventListener shop_listner;

    private ListView messagesView;
    private  FirebaseListAdapter mAdapter;

    private My_app app;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    //переменные от которых зависит загрузка
    private  String shopname_load = "noname_default";
    private  String tipe_shop = "default";
    private Boolean Show_modifibutton=false;



    @BindView(R.id.ShopPicPicture)
    ImageView mShopPicPicture;
    @BindView(R.id.Shop_text)
    TextView mShop_text;
    @BindView(R.id.Shop_tipe)
    TextView mShop_tipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        helper_Db_listenr = new Helper_Listers();
        helper_Db_listenr.registerCallBack(this);



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

        //создаём листнера по товарам в магазине... смотри callBackReturn_populateprofile
        shop_listner=helper_Db_listenr.chek_shop(auth.getCurrentUser(),shopname_load);
        app.getmDatabase().child("shops").child(shopname_load).addValueEventListener(shop_listner);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            intent.putExtra("shoptipe",tipe_shop);
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




private  void messagesView_set(){
    ///ЗАполнение списка товаров
    messagesView = (ListView) findViewById(R.id.listView_products);
    if (app.getmDatabase().child("shops").child(shopname_load).child("products") != null) {


        //адаптер ,  как заполнять список товаров в зависимости от типа магазина зависит от shopname_load
        mAdapter=Helper_shop.chtoto(tipe_shop,app.getmDatabase(),shopname_load,this);
        // создаем  листнер на позиции в списке товаров
        mylistner=helper_Db_listenr.Listner_lista_caledaria(mAdapter);
        messagesView.setOnItemClickListener(mylistner);


        messagesView.setAdapter(mAdapter);

    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // УДАЛЯТЬ ЛИСТНЕР ПРИ ПЕРЕХОДЕ НА СЛЕДУЮЩИЕ ЭКРАНЫ ЧТО БЫ НЕ ВЫПОЛНЯЛИСЬ ДЕЙСТВИЯ С ЗАКРЫТОЙ АКТИВИТИ
        app.getmDatabase().child("shops").child(shopname_load).removeEventListener(shop_listner);
        Log.w("shop_listner ВУДУЕ!", "Удалил листнер");

        if(mAdapter!=null)
        mAdapter.cleanup();

    }



    //Если с магазионм всё внорме начинается загрузка магазина, и установка переменных
       @Override
    public void callBackReturn_populateprofile(Shops shop, Boolean admin) {
        Show_modifibutton=admin;
        tipe_shop=shop.gettipe_of_shop();

        //заполняет шапку магазина
        Helper_shop.populateProfile(shop,this,mShopPicPicture,mShop_text,mShop_tipe);



        //Заполняет список товаров
          messagesView_set();
    }

// Нажатие на товар
    @Override
    public void callBack_producttouch_open(final String position) {







       Helper_product.touch_the_product(app.getmDatabase(),app.getauth().getCurrentUser(),position,shopname_load,getSupportFragmentManager(),tipe_shop);

    }
}
