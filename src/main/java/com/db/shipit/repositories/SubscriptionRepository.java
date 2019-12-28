package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.models.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;

@Repository
public class SubscriptionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerRepository customerRepository;

    public List<Subscription> getSubscriptionByID(String customerID){
        List<Subscription> subscriptions = jdbcTemplate.query("SELECT * FROM Subscription AS S WHERE S.ID = ? ORDER BY S.subscription_number DESC", new Object[]{customerID}, new BeanPropertyRowMapper(Subscription.class));

        return subscriptions;
    }

    public void renewSubscription() throws ParseException { //ADD COST LOGIC
        List<Subscription> subscriptions = getSubscriptionByID(currentUser.getID());
        Subscription latestSubscription = subscriptions.get(0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.MONTH, 1);
        Date newDate = cal.getTime();
        String newDateString = formatter.format(newDate);

        int cost;
        switch(latestSubscription.getSubscriptionTier()) {
            case 5:
                cost = 50;
                break;
            case 10:
                cost = 80;
                break;
            case 15:
                cost = 100;
                break;
            default:
                cost = 50;
        }

        jdbcTemplate.update("UPDATE Subscription SET end_date = ? WHERE ID = ? AND subscription_number = ?", newDateString, latestSubscription.getID(), latestSubscription.getSubscriptionNumber());
        jdbcTemplate.update("UPDATE Subscription SET used_package_rights = ? WHERE ID = ? AND subscription_number = ?", 0, latestSubscription.getID(), latestSubscription.getSubscriptionNumber());
        customerRepository.changeCustomerBalance(-cost);
    }

    public void addSubscription(int tier) throws ParseException {
        List<Subscription> subscriptions = getSubscriptionByID(currentUser.getID());
        Subscription latestSubscription = subscriptions.get(0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.MONTH, 1);
        Date newDate = cal.getTime();
        String todayString = formatter.format(today);
        String newDateString = formatter.format(newDate);

        jdbcTemplate.update("INSERT INTO Subscription VALUES (?,?,?,?,?,?,?)", latestSubscription.getID(), latestSubscription.getSubscriptionNumber() + 1,
                                                                                    tier, 0, todayString, newDateString, true);
    }

    public void cancelSubscription() throws ParseException {
        List<Subscription> subscriptions = getSubscriptionByID(currentUser.getID());
        Subscription latestSubscription = subscriptions.get(0);

        jdbcTemplate.update("UPDATE Subscription SET is_active = ? WHERE ID = ? AND subscription_number = ?", false, latestSubscription.getID(), latestSubscription.getSubscriptionNumber());
    }

}
