package ru.akov.buddyinshot;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 01.09.2016.
 */
public class Helper_product extends AppCompatActivity {

    static void populate_barbershop_product(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load,FragmentManager manager) {

        final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 3, 2013);
        dialogCaldroidFragment.setCancelable(true);

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));

        dialogCaldroidFragment.setArguments(args);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date1, View view) {
                if(user!=null){
                    if (date1.before(Calendar.getInstance().getTime())){

                        Snackbar.make(view,"Выбеи другую дату", Snackbar.LENGTH_LONG).show();
                    }

                    else {
                  //      showSnackbar("Выбрал дату" + date1.toString());
                        Snackbar.make(view,"Выбрал дату" + date1.toString(), Snackbar.LENGTH_LONG).show();
                  //      MydisableDateList.add(date1);
                        String month = (String) android.text.format.DateFormat.format("MM", date1); //06
                        String year = (String) android.text.format.DateFormat.format("yyyy", date1); //2013
                        String day = (String) android.text.format.DateFormat.format("dd", date1);
                        String zapis = year + month + day;
                        MyDate_format my_date = new MyDate_format(month, year, day);
                        dialogCaldroidFragment.getDialog().dismiss();
                        // ЗАполеяю в базе поля _ в самом магащзине и надо добавить пользователю в личной папке !!!
                        // app.getmDatabase().child("shops").child(shopname_load).child("products").child(myposition).child("workdays").push().setValue(my_date);}
                        mDatabase.child("shops").child(shopname_load).child("products").child(myposition).child("workdays").child(zapis).push().setValue(user.getUid());
                    }
                }
                else{
                    //showSnackbar("ЗАлогинтесь");
                    Snackbar.make(view, "ЧТО БЫ ВЫБРАТЬ ДАТУ ЗАЛОГИНТЕСЬ", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {

            }

            @Override
            public void onCaldroidViewCreated() {


            }

        };

        dialogCaldroidFragment.setCaldroidListener(listener);
        dialogCaldroidFragment.show(manager, "TAG");



    }

    static void touch_the_product(final String myposition, String tipe_shop) {

        String tipe=tipe_shop;
        switch (tipe){
        case "barbershop": ;
        break;}

    }

}