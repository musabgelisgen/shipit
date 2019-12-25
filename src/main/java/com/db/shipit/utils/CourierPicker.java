package com.db.shipit.utils;

public class CourierPicker {

    public static String getRandomCourierName(){
            String[] couries = new String[]{"Cafer", "Cengiz", "Caghan", "Cagil", "Cuneyt", "Can"};
            int index = (int)(Math.random() * couries.length);
            return couries[index];
    }
}
