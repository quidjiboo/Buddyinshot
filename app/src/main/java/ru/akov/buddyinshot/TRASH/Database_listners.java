package ru.akov.buddyinshot.TRASH;

import com.google.firebase.auth.FirebaseAuth;

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
