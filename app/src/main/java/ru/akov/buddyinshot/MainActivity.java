package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String UNCHANGED_CONFIG_VALUE = "CHANGE-ME";

    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;
   
    Chek_status_online dsf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.v("AKOV", "!!!!!!!Подключены!!!!!!!!!!" + user.getUid());
                    // User is signed in

                } else {
                    // User is signed out
                    Log.v("AKOV", "НЕ ПОДКЛЮЧЕНЫ");
                }
                // ...
            }
        };


        auth   = FirebaseAuth.getInstance();

      /*

        if (auth.getCurrentUser() != null) {
            showSnackbar(auth.getCurrentUser().toString());
            // already signed in
        } else {

            showSnackbar("НЕ ПОКДЛЮЧЕН!");
            // not signed in
        }*/

        setContentView(R.layout.activity_main);
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


//dfgdfgfderwer
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
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
        
        if(auth.getCurrentUser() == null){
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setTheme(AuthUI.getDefaultTheme())
                            .setProviders(AuthUI.GOOGLE_PROVIDER)
                            .setTosUrl("https://www.google.com/policies/terms/")
                            .build(),
                    100);}

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
      //      startActivity(SignedInActivity.createIntent(this));






            Intent intent = new Intent(MainActivity.this, Buddylist.class);
            startActivity(intent);
            finish();
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            showSnackbar("Sign in cancelle");
            return;
        }

        showSnackbar("Unknown response from AuthUI sign-in");
    }

    public void logout_action(View view) {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(android.R.id.content),"разлогинились", Snackbar.LENGTH_LONG).show();

                        } else {

                        }
                    }
                });

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
}
