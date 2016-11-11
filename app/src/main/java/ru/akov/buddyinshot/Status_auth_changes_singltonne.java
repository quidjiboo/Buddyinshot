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

import ru.akov.buddyinshot.Tipes_of_DATA.Product;
import ru.akov.buddyinshot.Tipes_of_DATA.Product_varibles_default;

import ru.akov.buddyinshot.Tipes_of_DATA.Shops;
import ru.akov.buddyinshot.Tipes_of_DATA.User;

/**
 * Created by Alexandr on 09.07.2016.
 */
 public class Status_auth_changes_singltonne {
    private static Status_auth_changes_singltonne instance;

    private Status_auth_changes_singltonne(){

    }
    public static synchronized Status_auth_changes_singltonne getInstance() {
        if(instance==null){
            instance = new Status_auth_changes_singltonne();   /// спорное решение !!!
        }
        return instance;
    }



    public  void  Chek_status_online_user_siglevalue_listner(final DatabaseReference mDatabase,final FirebaseUser user ) {

         final String TAG = "NewPostActivity";
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       if(!dataSnapshot.exists()) {
                           User msg = new User(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
                           mDatabase.child("users").child(userId).setValue(msg);
                           Log.v("AKOV", "NO USERS IN DATABASE");

                      //     add_default_barbeshop(mDatabase,user);

                       }

                        // ...

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });



    }
    public   void add_default_barbeshop(final DatabaseReference mDatabase,final FirebaseUser user ){
        //дефолтовыймагазин
        final String TAG = "ДЕФОЛТНЫЙ БАРБЕР ШОП";
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).child("shops").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists()) {

                            Shops msg = new Shops("noname", "notipe", "https://firebasestorage.googleapis.com/v0/b/test-base-soc-net.appspot.com/o/defaultshop.png?alt=media&token=92cc5bdb-bb0d-4a03-a292-da6ef5eb622d",userId);
                        //    String key=mDatabase.child("shops").push().getKey();
                            String key1 =   mDatabase.child("users").child(userId).child("shops").push().getKey();
                            mDatabase.child("users").child(userId).child("shops").child(key1).setValue(Boolean.TRUE);
                            mDatabase.child("shops").child(key1).setValue(msg);

                         /*   Product product = new Product("default","0.0","https://firebasestorage.googleapis.com/v0/b/test-base-soc-net.appspot.com/o/shopping-paper-bag-outline_318-39786.png?alt=media&token=93a2373e-1336-4fbe-9268-924db09e4fb9");
                            String key = mDatabase.child("shops").child(key1).child("products").push().getKey();
                            mDatabase.child("shops").child(key1).child("products").child(key).setValue(product);

                            Product_varibles_default variebl = new Product_varibles_default("default");
                            mDatabase.child("shops").child(key1).child("products").child(key).child("variebles").setValue(variebl);*/

                            Log.v("AKOV", "NO SHOPS");
                        }
                        // ...

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }



    public   void login_action(FirebaseAuth auth,Activity activity) {

        if(auth.getCurrentUser() == null){

            activity.startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setTheme(AuthUI.getDefaultTheme())
                            .setProviders(AuthUI.GOOGLE_PROVIDER)
                            .setTosUrl("https://www.google.com/policies/terms/")
                            .build(),
                    100);}

    }

    public    void logout_action(Activity activity,final View view) {


        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view,"разлогинились", Snackbar.LENGTH_LONG).show();

                        } else {

                        }
                    }
                });
    }
}
