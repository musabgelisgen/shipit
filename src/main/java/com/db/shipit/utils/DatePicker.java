package com.db.shipit.utils;

public class DatePicker {

    public static String getDate(){
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(dt);
    }
}
