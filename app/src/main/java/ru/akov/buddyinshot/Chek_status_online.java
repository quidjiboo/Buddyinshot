package ru.akov.buddyinshot;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Alexandr on 09.07.2016.
 */
class Chek_status_online {

    void Chek_status_online_user_siglevalue_listner(DatabaseReference mDatabase,FirebaseUser user ) {

         final String TAG = "NewPostActivity";
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                     //   User user = dataSnapshot.getValue(User.class);

                       if(dataSnapshot.exists())
                           Log.v("AKOV", "NO USERS IN DATABASE");
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

    }
}
