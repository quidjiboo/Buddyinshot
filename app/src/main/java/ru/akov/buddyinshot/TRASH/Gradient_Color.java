package ru.akov.buddyinshot.TRASH;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import ru.akov.buddyinshot.R;

/**
 * Created by User on 12.09.2016.
 */
public class Gradient_Color extends AppCompatActivity {
    public static int get_my_colo(float x){

        int  new_col = 0xfffffff;
      if (0<x&&x<=0.25){new_col =   R.color.MY_RED_2;}
        else
        if (x>0.25&&x<=0.5){new_col =  R.color.MY_RED_3;}
        else
        if (x>0.5&&x<=0.75){new_col =  R.color.MY_RED_5;}
        else
        if (x>0.75&&x<=1){new_col = R.color.MY_RED_6;}
else{new_col = R.color.MY_GOLD;}

        return new_col;


    }
}
