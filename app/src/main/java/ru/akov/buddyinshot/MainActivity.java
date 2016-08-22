package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements  MyCallback {
    private ValueEventListener shop_list_listner;
    private static final String UNCHANGED_CONFIG_VALUE = "CHANGE-ME";
    private My_app app;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
            protected void populateView(View view, Shops chatMessage, int position) {

                ((TextView)view.findViewById(android.R.id.text1)).setText(chatMessage.getname());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chatMessage.gettipe_of_shop());

            }
        };

        messagesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
          //      View textView = (View) itemClicked;

                TextView textVie1   = (TextView)itemClicked.findViewById(android.R.id.text1);

             //  String strText = textView.findViewById(android.R.id.text1).toString(); // получаем текст нажатого элемента
                showSnackbar("выбран магазин"+textVie1.getText().toString());
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
        this.auth=app.getauth();


       // this. mAuthListener=app.getmAuthListener();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public void  curent_user_action(View view) {
        if (auth.getCurrentUser() != null) {
            auth = FirebaseAuth.getInstance();
            Snackbar.make(findViewById(android.R.id.content), auth.getCurrentUser().toString(), Snackbar.LENGTH_LONG).show();
        } else {

            showSnackbar("НЕ ПОКДЛЮЧЕН!");
            // not signed in
        }
    }


    public void login_action(View view) {

        Status_auth_changes_singltonne.getInstance().login_action(auth,this);
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
     //   Database_listners.getInstance().Authlisters_removers(mAuthListener,auth);
      //  auth.removeAuthStateListener(mAuthListener);
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
        shop_list_listner = Status_auth_changes_singltonne.getInstance().shop_list_listner(app.getmDatabase(), shop_list_listner);

    }

    @Override
    public void callBackReturnofff() {
        if(shop_list_listner!=null)
        app.getmDatabase().removeEventListener(shop_list_listner);
   //     Status_auth_changes_singltonne.getInstance().remove_shop_list_listner(app.getmDatabase(),shop_list_listner);
    }
}
