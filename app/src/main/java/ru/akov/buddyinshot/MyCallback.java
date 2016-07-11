package ru.akov.buddyinshot;

import com.google.android.gms.common.api.Status;

/**
 * Created by User on 21.04.2016.
 */
interface MyCallback {
    void callBackReturn();
    void lastlocation();
    void badpremission();
    void badpremissioninsettings_gps(Status status);
}