package ru.akov.buddyinshot;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.akov.buddyinshot.Tipes_of_DATA.MyDate_format;

/**
 * Created by User on 12.09.2016.
 */
public class Calendarik_creator {

    static CaldroidFragment create(final DatabaseReference mDatabase, final FirebaseUser user, final String myposition, final String shopname_load){
        final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 3, 2013);
        dialogCaldroidFragment.setCancelable(true);

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));

        dialogCaldroidFragment.setArguments(args);
        GregorianCalendar calendar = new GregorianCalendar(2016, 8, 17);
        Date hireDay = calendar.getTime();
        dialogCaldroidFragment.setTextColorForDate(R.color.caldroid_light_red,hireDay);
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(final Date date1, final View view) {

                Boolean add = false;
                if(user!=null){
                    if (date1.before(Calendar.getInstance().getTime())){

                        Snackbar.make(view,"Выбеи другую дату", Snackbar.LENGTH_LONG).show();
                    }
                    else {



                        final  String month = (String) android.text.format.DateFormat.format("MM", date1); //06
                        final String year = (String) android.text.format.DateFormat.format("yyyy", date1); //2013
                        final   String day = (String) android.text.format.DateFormat.format("dd", date1);
                        final String zapis = year + month + day;


                        mDatabase.child("shops").child(shopname_load).child("products").child(myposition).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()) {
                                            Long     x = dataSnapshot.child("workdays").child(zapis).getChildrenCount();
                                            Long     y = ((Long) dataSnapshot.child("variebles").child("clients").getValue());

                                            Log.v("xxxx","X=" +  x.toString());
                                            Log.v("yyyy", "Y=" + y.toString());
                                            if(x!=null&&x<y)
                                            {
                                                Snackbar.make(view,"Выбрал дату" + date1.toString(), Snackbar.LENGTH_LONG).show();
                                                //      MydisableDateList.add(date1);
                                                MyDate_format my_date = new MyDate_format(month, year, day);

                                                // ЗАполеяю в базе поля _ в самом магащзине и надо добавить пользователю в личной папке !!!
                                                // app.getmDatabase().child("shops").child(shopname_load).child("products").child(myposition).child("workdays").push().setValue(my_date);}
                                                mDatabase.child("shops").child(shopname_load).child("products").child(myposition).child("workdays").child(zapis).push().setValue(user.getUid());

                                                dialogCaldroidFragment.getDialog().dismiss();
                                            }
                                            else{Snackbar.make(view,"ОПА ОПА ОПААА ", Snackbar.LENGTH_LONG).show();}
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.w("уккщк", "getUser:onCancelled", databaseError.toException());
                                    }
                                });








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


        return dialogCaldroidFragment;
    }
}