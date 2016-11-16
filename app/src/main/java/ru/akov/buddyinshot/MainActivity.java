package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;

import ru.akov.buddyinshot.Tipes_of_DATA.Shops;

public class MainActivity extends AppCompatActivity implements  MyCallbackOLD {

    private static final String UNCHANGED_CONFIG_VALUE = "CHANGE-ME";
    private My_app app;


    private  ListView messagesView;
    private  FirebaseListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        setContentView(R.layout.activity_main);

        app = ((My_app) getApplicationContext());
        app.registerCallBack(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_scr(getCurrentFocus());
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        if (!isGoogleConfigured()) {
            showSnackbar("Configuration is required _goo - see README.md");
        }
        else{
            //Snackbar.make(findViewById(android.R.id.content), R.string.default_web_client_id, Snackbar.LENGTH_LONG).show();

        }


        messagesView = (ListView) findViewById(R.id.list_of_shops);

         mAdapter = new FirebaseListAdapter<Shops>(this, Shops.class, android.R.layout.simple_list_item_2, app.getmDatabase().child("shops")) {
            @Override
            protected void populateView(View view, Shops shop, int position) {

                ((TextView)view.findViewById(android.R.id.text1)).setText(shop.getname());
                //Преобразование типа с английского на русскай
                String tipeofshop="noname";
                switch (shop.gettipe_of_shop()) {
                    case "barbershop":  tipeofshop = "парикмахерская";
                        break;}

                ((TextView)view.findViewById(android.R.id.text2)).setText(tipeofshop);

            }
        };

        messagesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
          //      View textView = (View) itemClicked;
                Log.v("AKOV",  mAdapter.getRef(position).getKey());
                TextView textVie1   = (TextView)itemClicked.findViewById(android.R.id.text1);

             //  String strText = textView.findViewById(android.R.id.text1).toString(); // получаем текст нажатого элемента
                showSnackbar("выбран магазин"+textVie1.getText().toString());


                Intent intent = new Intent(MainActivity.this, Loadtest.class);
                intent.putExtra("shopname", mAdapter.getRef(position).getKey().toString());
                startActivity(intent);
                finish();
              /*  if(strText.equalsIgnoreCase(getResources().getString(R.string.name1))) {
                    // Запускаем активность, связанную с определенным именем кота
                    startActivity(new Intent(this, BarsikActivity.class));
                }*/
            }
        });

        messagesView.setAdapter(mAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        app.createmAuthListener();

    //    this.auth=app.getauth(); // убрал зачемто
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }


    public void new_shop_action(View view) {

CreateBarbershop createBarbershopr = new CreateBarbershop();
        createBarbershopr.create_shop();
    }



    public void  curent_user_action(View view) {
        if (app.getauth().getCurrentUser() != null) {

        //    auth = FirebaseAuth.getInstance();
            Snackbar.make(findViewById(android.R.id.content),app.getauth().getCurrentUser().toString(), Snackbar.LENGTH_LONG).show();
        } else {

            showSnackbar("НЕ ПОКДЛЮЧЕН!");
            // not signed in
        }
    }


    public void login_action(View view) {

        Status_auth_changes_singltonne.getInstance().login_action(app.getauth(),this);
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            handleSignInResponse(resultCode, data);




            return;
        }

       showSnackbar("Unexpected onActivityResult response code");
    }

    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

          //      DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //    Status_auth_changes_singltonne.getInstance().Chek_status_online_user_siglevalue_listner(mDatabase,auth.getCurrentUser());
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            showSnackbar("Sign in cancelle");
            return;
        }

        showSnackbar("Unknown response from AuthUI sign-in");
    }

    public void logout_action(View view) {
        Status_auth_changes_singltonne.getInstance().logout_action(this,view);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
    public void next_scr(View view) {

        Intent intent = new Intent(MainActivity.this, Buddylist.class);

        startActivity(intent);

        this.finish();
    }


    @MainThread
    private boolean isGoogleConfigured() {
        return !UNCHANGED_CONFIG_VALUE.equals(
                getResources().getString(R.string.default_web_client_id));
    }
    @MainThread
    private void showSnackbar(String errorMessage) {
        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void callBackReturn() {


    }



    @Override
    public void callBackReturnofff() {

    }
}
