package ru.akov.buddyinshot.TRASH;

/**
 * Created by Alexandr on 13.03.2016.
 */
public class Accont_info_my_sington {

    private String name;
    private static Accont_info_my_sington instance;
    private String mail;
    private String GPSLatitude;
    private String GPSLongitude;

    private Accont_info_my_sington(){
        name="";
        mail="";
        GPSLatitude="";
        GPSLongitude="";
    }

    public void setGPS(String gpsx, String gpsy){
        this.GPSLatitude=gpsx;this.GPSLongitude=gpsy;

    }
    public String getGPSLatitude(){
        return GPSLatitude;
    }
    public String getGPSLongitude(){
        return GPSLongitude;
    }

    public static void  initInstance() {
        if(instance==null){
            instance = new Accont_info_my_sington();   /// спорное решение !!!
        }
    }

    public static synchronized Accont_info_my_sington getInstance() {

        return instance;
    }

    public String getname(){
        return name;
    }
    public void setName(String name){

        this.name=name;

    }
    public String getMail(){
        return mail;
    }
    public void setMail(String mail){

        this.mail=mail;

    }

    public void clerar(){
        name="ВЫ НЕ ПОДКЛЮЧЕНЫ!";

        mail="";
    }




}
