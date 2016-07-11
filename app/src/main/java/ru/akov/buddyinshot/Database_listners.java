package ru.akov.buddyinshot;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Alexandr on 09.07.2016.
 */
public class Database_listners {
    private static Database_listners instance;

    private Database_listners(){

    }
    public static synchronized Database_listners getInstance() {
        if(instance==null){
            instance = new Database_listners();   /// спорное решение !!!
        }
        return instance;
    }

    public  void Authlisters_removers(FirebaseAuth.AuthStateListener mAuthListener,FirebaseAuth auth){
        auth.removeAuthStateListener(mAuthListener);
    }
}
