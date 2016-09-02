package ru.akov.buddyinshot;

import ru.akov.buddyinshot.Tipes_of_DATA.Shops;

/**
 * Created by User on 21.04.2016.
 */
interface MyCallback {

    void callBackReturn_populateprofile(Shops shop, Boolean admin);
    void callBack_producttouch_open(String myposition);
}