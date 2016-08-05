package ru.akov.buddyinshot;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by User on 21.03.2016.
 */
public class My_app extends Application {


    private DatabaseReference mDatabase;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
public FirebaseAuth getauth(){
    return  auth;
}
    public DatabaseReference getmDatabase(){
        return  mDatabase;
    }
    public FirebaseAuth.AuthStateListener  getmAuthListener(){
        return  mAuthListener;
    }

    // вынести в отдельный класс
public void createmAuthListener () {
    if (mAuthListener == null) {
        Log.v("AKOV", "Повесил листер!!!!!");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.v("AKOV", "!!!!!!!Подключены!!!!!!!!!!" + user.getUid());


                    Status_auth_changes_singltonne.getInstance().Chek_status_online_user_siglevalue_listner(mDatabase, auth.getCurrentUser());
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
    };
}

}
