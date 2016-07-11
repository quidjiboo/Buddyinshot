package ru.akov.buddyinshot;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by User on 21.03.2016.
 */
public class My_app extends Application {



    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    MyCallback myCallback;

    void registerCallBack(MyCallback callback){
        this.myCallback = callback;
    }


    @Override
    public void onCreate() {
       super.onCreate();

        auth   = FirebaseAuth.getInstance();

        Log.v("AKOV", "Повесил листер!!!!!");
        mAuthListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.v("AKOV", "!!!!!!!Подключены!!!!!!!!!!" + user.getUid());



                    //     next_scr(getCurrentFocus());
                    // User is signed in


                } else {
                    // User is signed out
                    Log.v("AKOV", "НЕ ПОДКЛЮЧЕНЫ");
                }
                // ...
            }
        };
        auth.addAuthStateListener(mAuthListener);
    }
public FirebaseAuth getauth(){
    return  auth;
}

    public FirebaseAuth.AuthStateListener  getmAuthListener(){
        return  mAuthListener;
    }


}
