package ru.akov.buddyinshot;

import java.util.ArrayList;

import ru.akov.buddyinshot.Tipes_of_DATA.Shops;

/**
 * Created by User on 21.04.2016.
 */
public interface MyCallback {

    void callBackReturn_populateprofile(Shops shop, Boolean admin);
    void callBack_producttouch_open(String myposition);
    void callBack_touchproduct_creat_calendarik(ArrayList buzy_dayz, float max_client,String position);

}