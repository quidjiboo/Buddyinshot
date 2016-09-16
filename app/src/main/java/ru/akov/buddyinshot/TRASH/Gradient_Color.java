package ru.akov.buddyinshot.TRASH;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import ru.akov.buddyinshot.My_app;
import ru.akov.buddyinshot.R;

/**
 * Created by User on 12.09.2016.
 */

// СДЕЛАТЬ СИНГЛТОН И ГЕНЕРИРОВАТЬ БИТМАПЫ!!!
public class Gradient_Color{


    private static Gradient_Color instance;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;
    private Drawable drawable4;
    private Drawable drawable5;
    private Drawable drawablegold;

    public static synchronized Gradient_Color getInstance() {
        if(instance==null){
            instance = new Gradient_Color();   /// спорное решение !!!
        }
        return instance;
    }
    Gradient_Color(){
  /*      //Bitmap bitmap = BitmapFactory.decodeResource(My_app.getContext().getResources(), R.color.MY_RED_6);
        Bitmap bitmap = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.BLUE);
        Drawable drawable2 = new BitmapDrawable(My_app.getContext().getResources(),bitmap );*/
        Bitmap bitmap = Bitmap.createBitmap(1, 1,
                Bitmap.Config.ARGB_8888);

        bitmap.eraseColor( ContextCompat.getColor(My_app.getContext(), R.color.MY_RED_2));
        drawable2 = new BitmapDrawable(My_app.getContext().getResources(),bitmap );

        bitmap = Bitmap.createBitmap(1, 1,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor( ContextCompat.getColor(My_app.getContext(), R.color.MY_RED_3));
        drawable3 = new BitmapDrawable(My_app.getContext().getResources(),bitmap );
        bitmap = Bitmap.createBitmap(1, 1,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor( ContextCompat.getColor(My_app.getContext(), R.color.MY_RED_4));
        drawable4 = new BitmapDrawable(My_app.getContext().getResources(),bitmap );
        bitmap = Bitmap.createBitmap(1, 1,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor( ContextCompat.getColor(My_app.getContext(), R.color.MY_RED_5));
        drawable5 = new BitmapDrawable(My_app.getContext().getResources(),bitmap );
        bitmap = Bitmap.createBitmap(1, 1,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor( ContextCompat.getColor(My_app.getContext(), R.color.MY_GOLD));
        drawablegold = new BitmapDrawable(My_app.getContext().getResources(),bitmap );



    }
    public Drawable get_my_colo(float x){
        //TO DO ну както надо что то делать
        Drawable new_col=null;

      if (0<x&&x<=0.25){new_col =   drawable2;}
        else
        if (x>0.25&&x<=0.5){new_col =  drawable3;}
        else
        if (x>0.5&&x<=0.75){new_col =  drawable4;}
        else
        if (x>0.75&&x<=1){new_col = drawable5;}
else
        if (x>1){new_col = drawablegold;}

        return new_col;


    }
}
