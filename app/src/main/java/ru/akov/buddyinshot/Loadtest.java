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
    private Helper_list helper;
    private ValueEventListener val;
    private ArrayList<Date> MydisableDateList;
    private ArrayList<String> MyStringdisableDateList;
    private String myposition;
    private Date zakaz;

    private ListView messagesView;
    private  FirebaseListAdapter mAdapter;

    private Boolean Show_modifibutton=false;
    private My_app app;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private  String shopname_load = "noname_default";

    @BindView(R.id.ShopPicPicture)
    ImageView mShopPicPicture;
    @BindView(R.id.Shop_text)
    TextView mShop_text;
    @BindView(R.id.Shop_tipe)
    TextView mShop_tipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        helper = new Helper_list();
        helper.registerCallBack(this);
        //Status_auth_changes_singltonne.getInstance().registerCallBack(this);

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

        helper.chek_shop(app.getmDatabase(),auth.getCurrentUser(),shopname_load);
        messagesView_set();

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
    private  void Listner_lista_caledaria(){

        mylistner = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textVie1 = (TextView) itemClicked.findViewById(android.R.id.text1);
                showSnackbar("Нажал на товар = " + textVie1.getText().toString());
                myposition = mAdapter.getRef(position).getKey().toString();

            }
        } ;


    }

    private  void calendar_creator(){

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
                        zakaz = date1;
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
        mAdapter = new FirebaseListAdapter<Product>(this, Product.class, android.R.layout.simple_list_item_2, app.getmDatabase().child("shops").child(shopname_load).child("products")) {
            @Override
            protected void populateView(View view, Product chatMessage, int position) {
                if (chatMessage != null) {
                    ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getname());
                    ((TextView) view.findViewById(android.R.id.text2)).setText(chatMessage.getprice());
                }
            }
        };

        messagesView.setOnItemClickListener(
                mylistner =  new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View itemClicked, int position,
                                    long id) {

                TextView textVie1 = (TextView) itemClicked.findViewById(android.R.id.text1);
                showSnackbar("Нажал на товар = " + textVie1.getText().toString());
                myposition = mAdapter.getRef(position).getKey().toString();
//массив с забитыми днями
/*                app.getmDatabase().child("shops").child(shopname_load).child("products").child(myposition).child("workdays").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                MyStringdisableDateList.clear();
                                if (dataSnapshot != null) {

                                    for (DataSnapshot RestNames : dataSnapshot.getChildren()) {

                                        //   MyStringdisableDateList.add(dataSnapshot.getValue().toString());
                                        Log.w("В СПИСОК ЗАнятых дел", dataSnapshot.getValue().toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("В заказах пусто", "getUser:onCancelled", databaseError.toException());
                            }
                        });*/



                calendar_creator();

            }
        });


        messagesView.setAdapter(mAdapter);

    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }


    @MainThread
    private void showSnackbar(String errorMessage) {
        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void callBackReturn() {
        Show_modifibutton=true;
    }

    @Override
    public void callBackReturn_populateprofile(Shops shop) {
        helper.populateProfile(shop,this,mShopPicPicture,mShop_text,mShop_tipe);
    }

    @Override
    public void callBackReturnofff() {

    }
}
