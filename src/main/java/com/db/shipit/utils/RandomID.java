package com.db.shipit.utils;

import java.util.UUID;

public class RandomID {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }
}
