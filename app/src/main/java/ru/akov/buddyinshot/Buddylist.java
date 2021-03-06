package ru.akov.buddyinshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Buddylist extends AppCompatActivity   {
    private My_app app;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener mAuthListener;



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
        ButterKnife.bind(this);



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
         /*       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                scr_load(getCurrentFocus());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



      //  auth   = FirebaseAuth.getInstance();


        app = ((My_app) getApplicationContext());

       populateProfile();






    }
    public void put_to_storage(View view) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://test-base-soc-net.appspot.com");
        StorageReference mountainsRef = storageRef.child("mountains.jpg");
        StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");

        mUserProfilePicture.setDrawingCacheEnabled(true);
        mUserProfilePicture.buildDrawingCache();
        Bitmap bitmap = mUserProfilePicture.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });


    }
    public void logout_action(View view) {
        Status_auth_changes_singltonne.getInstance().logout_action(this,view);
        Intent intent = new Intent(Buddylist.this, MainActivity.class);

        startActivity(intent);


        this.finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
         this.auth=app.getauth();
        this. mAuthListener=app.getmAuthListener();
     //   auth.addAuthStateListener(mAuthListener);
    }

    public void next_scr(View view) {

        Intent intent = new Intent(Buddylist.this, MainActivity.class);

        startActivity(intent);


        this.finish();

    }
    public void scr_load(View view) {

        Intent intent = new Intent(Buddylist.this, Loadtest.class);

        startActivity(intent);


        this.finish();

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
