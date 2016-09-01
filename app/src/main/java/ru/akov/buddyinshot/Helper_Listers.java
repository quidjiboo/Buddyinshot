package ru.akov.buddyinshot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_Listers    {

    MyCallback myCallback;

    void registerCallBack(MyCallback callback){
        this.myCallback = callback;
    }
    public ValueEventListener chek_shop(FirebaseUser user,String string) {
        final FirebaseUser user1=user;
        ValueEventListener Xdd;
     //   mDatabase.child("shops").child(string).addListenerForSingleValueEvent(
               Xdd = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Shops shop = dataSnapshot.getValue(Shops.class);
                        if(shop!=null){
                            Boolean admin=false;
                            if (shop != null && user1 != null && shop.getadmin().toString().equals(user1.getUid()))
                                admin=true;
                            myCallback.callBackReturn_populateprofile(shop,admin);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("OSHIBCAK", "getUser:onCancelled", databaseError.toException());
                    }
                };
        //);
    return  Xdd;}


    public AdapterView.OnItemClickListener   Listner_lista_caledaria(final FirebaseListAdapter mAdapter){

        AdapterView.OnItemClickListener  mylistner = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textVie1 = (TextView) itemClicked.findViewById(android.R.id.text1);
               // showSnackbar("Нажал на товар = " + textVie1.getText().toString());
                String myposition = mAdapter.getRef(position).getKey().toString();
                myCallback.callBack_producttouch_open(myposition);
            }
        } ;

return mylistner;
    }

}
