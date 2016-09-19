package ru.akov.buddyinshot.On_product_of_barbershop_click;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.akov.buddyinshot.MyCallback;
import ru.akov.buddyinshot.Tipes_of_DATA.Buzy_day;
import ru.akov.buddyinshot.Tipes_of_DATA.Product_varibles_barbershop;

/**
 * Created by User on 16.09.2016.
 */
public class  Touch_product extends AppCompatActivity {
    MyCallback myCallback;

    public void registerCallBack(MyCallback callback) {
        this.myCallback = callback;
    }

    public void choosing_barbershop_product(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load, final FragmentManager manager) {


        mDatabase.child("shops").child(shopname_load).child("products").child(myposition).child("variebles").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Product_varibles_barbershop newComment = dataSnapshot.getValue(Product_varibles_barbershop.class);
                        System.out.println("ВАРИЕБЛЕСЫЫ!!" + newComment.getclients());
                        choosing_barbershop_product_state_two(mDatabase,user,myposition,shopname_load,manager,newComment.getclients());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("уккщк", "getUser:onCancelled", databaseError.toException());
                    }
                });


    }
    public void choosing_barbershop_product_state_two(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load, final FragmentManager manager, final float max_client) {
        mDatabase.child("shops").child(shopname_load).child("products").child(myposition).child("workdays").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        ArrayList buzy_dayz = new ArrayList();

                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {


                            Date date =null;
                            System.out.println( postSnapshot.getKey().toString());
                            String stringDateFormat = "yyyyMMdd";
                            SimpleDateFormat format = new SimpleDateFormat(stringDateFormat, Locale.US);

                            try {
                                date = format.parse( postSnapshot.getKey().toString());

                                System.out.println(date.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Buzy_day X_day = new  Buzy_day(date, (int) postSnapshot.getChildrenCount());
                            buzy_dayz.add(X_day);
                            // TODO: handle the post



                        }

                        /// TODO ДОБАВИТЬ СПИСОК ЗАНЯТЫХ ДНЕЙ , И ТОЛЬКО ПОСЛЕ ЭТОГО ЗАПОЛНЯТЬ КАЛЕНДАРИК! может и в реальном времени потому что при нажатии стоит одиночный листнер

                        /// сохдёте окно календарика!
                        myCallback.callBack_touchproduct_creat_calendarik(buzy_dayz,max_client,myposition);
                   /*     CaldroidFragment calendar = Calendarik_creator.create(buzy_dayz,max_client);

                        calendar.setCaldroidListener(Calendarik_creator.createlistner(mDatabase,user,myposition,shopname_load));
                        calendar.show(manager, "TAG");*/
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("уккщк", "getUser:onCancelled", databaseError.toException());
                    }
                });






    }
}
