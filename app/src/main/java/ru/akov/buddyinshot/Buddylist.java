package ru.akov.buddyinshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Buddylist extends AppCompatActivity   {
    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.user_profile_picture)
    ImageView mUserProfilePicture;
    @BindView(R.id.user_email)
    TextView mUserEmail;
    @BindView(R.id.user_display_name)
    TextView mUserDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddylist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    next_scr(getCurrentFocus());

                }
            });
        }
        //тест для гита
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        auth   = FirebaseAuth.getInstance();

        mAuthListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.v("AKOV", "!!!!!!!Подключены!!!!!!!!!!" + user.getUid());
                    // User is signed in
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    Chek_status_online_user_siglevalue_listner(mDatabase,user);

                } else {
                    // User is signed out
                    Log.v("AKOV", "НЕ ПОДКЛЮЧЕНЫ");
                }
                // ...
            }
        };


   //     populateProfile();
    }
    void Chek_status_online_user_siglevalue_listner(DatabaseReference mDatabase, FirebaseUser user ) {
        Log.v("AKOV", "ПРОВЕРЯЮ СТАТУС");
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
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    public void next_scr(View view) {
        this.finish();
        Intent intent = new Intent(Buddylist.this, MainActivity.class);

        startActivity(intent);


    }
    @MainThread
    private void populateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

if(user!=null){

// при разлогиненом не заполняются поля...!!

        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .fitCenter()
                    .into(mUserProfilePicture);


        //    mUserEmail.setText(
        //            TextUtils.isEmpty(user.getEmail()) ? "No email" : user.getEmail());
       //     mUserEmail.setText("NENENNENENEN");
        }
        else{Log.v("AKOV","NO URL FOR FOTO");}

        /*FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null) {
            // Name, email address, and profile photo Url
            String name = user1.getDisplayName();
            String email = user1.getEmail();
            Uri photoUrl = user1.getPhotoUrl();
            if(photoUrl!=null)
            Log.v("AKOV",photoUrl.toString());
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }*/


        mUserEmail.setText(
                          TextUtils.isEmpty(user.getEmail()) ? "No email" : user.getEmail());
        mUserDisplayName.setText(
                TextUtils.isEmpty(user.getDisplayName()) ? "No display name" : user.getDisplayName());




    }}


}
