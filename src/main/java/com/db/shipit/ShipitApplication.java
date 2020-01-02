package com.db.shipit;

import com.db.shipit.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShipitApplication {
    public static User currentUser;

    public static void main(String[] args) {
       // currentUser = new User().setID("51572f");
        //currentUser = null;
        SpringApplication.run(ShipitApplication.class, args);
    }

}
