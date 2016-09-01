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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    private  String tipe_shop = "noname_default";
    private Boolean Show_modifibutton=false;

//временные
    private ArrayList<Date> MydisableDateList;
    private ArrayList<String> MyStringdisableDateList;

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


        MyStringdisableDateList = new ArrayList<>();
        MydisableDateList = new ArrayList<>();
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
        app.getmDatabase().child("shops").child(shopname_load).addListenerForSingleValueEvent(shop_listner);
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


    private  void calendar_creator(final String myposition, String tipe_shop){

        final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 3, 2013);
        dialogCaldroidFragment.setCancelable(true);

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        if (!MydisableDateList.isEmpty()) {
            Log.w("MydisableDateList!!!!!", MydisableDateList.get(0).toString());
            //  dialogCaldroidFragment.setDisableDates(MydisableDateList);
            dialogCaldroidFragment.setDisableDatesFromString(MyStringdisableDateList);
        }
        dialogCaldroidFragment.setArguments(args);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date1, View view) {
                if(auth.getCurrentUser()!=null){
                    if (date1.before(Calendar.getInstance().getTime())){
                        showSnackbar("Выбеи другую дату");
                    }

                    else {
                        showSnackbar("Выбрал дату" + date1.toString());
                        MydisableDateList.add(date1);
                        String month = (String) android.text.format.DateFormat.format("MM", date1); //06
                        String year = (String) android.text.format.DateFormat.format("yyyy", date1); //2013
                        String day = (String) android.text.format.DateFormat.format("dd", date1);
                        String zapis = year + month + day;
                        MyDate_format my_date = new MyDate_format(month, year, day);
                        dialogCaldroidFragment.getDialog().dismiss();
                        // ЗАполеяю в базе поля _ в самом магащзине и надо добавить пользователю в личной папке !!!
                        // app.getmDatabase().child("shops").child(shopname_load).child("products").child(myposition).child("workdays").push().setValue(my_date);}
                        app.getmDatabase().child("shops").child(shopname_load).child("products").child(myposition).child("workdays").child(zapis).push().setValue(app.getauth().getCurrentUser().getUid());
                    }
                }
                else{
                    //showSnackbar("ЗАлогинтесь");
                    Snackbar.make(view, "ЧТО БЫ ВЫБРАТЬ ДАТУ ЗАЛОГИНТЕСЬ", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {

            }

            @Override
            public void onCaldroidViewCreated() {


            }

        };

        dialogCaldroidFragment.setCaldroidListener(listener);
        dialogCaldroidFragment.show(getSupportFragmentManager(), "TAG");

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
        if(mAdapter!=null)
        mAdapter.cleanup();
    }


    @MainThread
    private void showSnackbar(String errorMessage) {
        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    //Если с магазионм всё внорме начинается загрузка магазина, и установка переменных
       @Override
    public void callBackReturn_populateprofile(Shops shop,Boolean admin) {
        Show_modifibutton=admin;
        tipe_shop=shop.gettipe_of_shop();

        //заполняет шапку магазина
        Helper_shop.populateProfile(shop,this,mShopPicPicture,mShop_text,mShop_tipe);



        //Заполняет список товаров
          messagesView_set();
    }
// Нажатие на товар
    @Override
    public void callBack_producttouch_open(String position) {

        calendar_creator(position,tipe_shop);
    }
}
