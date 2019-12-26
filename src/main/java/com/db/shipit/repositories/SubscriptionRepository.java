package com.db.shipit.repositories;

import com.db.shipit.models.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Subscription> getSubscriptionByID(String customerID){
        List<Subscription> subscriptions = jdbcTemplate.query("SELECT * FROM Subscription AS S WHERE S.ID = ? ORDER BY S.subscription_number DESC", new Object[]{customerID}, new BeanPropertyRowMapper(Subscription.class));

        return subscriptions;
    }
}
