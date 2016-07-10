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
final public class Status_auth_changes  {

    final static void Chek_status_online_user_siglevalue_listner(DatabaseReference mDatabase,FirebaseUser user ) {

         final String TAG = "NewPostActivity";
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                     //   User user = dataSnapshot.getValue(User.class);

                       if(!dataSnapshot.exists())
                           Log.v("AKOV", "NO USERS IN DATABASE");
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

    }



    final static  public void login_action(FirebaseAuth auth,Activity activity) {

        if(auth.getCurrentUser() == null){

            activity.startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setTheme(AuthUI.getDefaultTheme())
                            .setProviders(AuthUI.GOOGLE_PROVIDER)
                            .setTosUrl("https://www.google.com/policies/terms/")
                            .build(),
                    100);}

    }

    static  public void logout_action(Activity activity,View view) {
      final View  view1 = view;
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view1,"разлогинились", Snackbar.LENGTH_LONG).show();

                        } else {

                        }
                    }
                });
    }
}
