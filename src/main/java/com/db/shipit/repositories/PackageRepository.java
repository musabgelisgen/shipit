package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.models.Package;
import com.db.shipit.models.User;
import com.db.shipit.utils.CourierPicker;
import com.db.shipit.utils.DatePicker;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.db.shipit.ShipitApplication.currentUser;

@Repository
public class PackageRepository {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Package> getAllPackages(Map<String, Boolean> modifications) {
        String id = currentUser.getID();

        List<Package> packages = jdbcTemplate.query("SELECT * FROM Package WHERE receiver_id = ? OR sender_id = ?", new Object[]{id, id},new BeanPropertyRowMapper(Package.class));
        return packages;
    }

    public void commitPackage(Package packet) {
        setPropertiesOfToInsert(packet);

        jdbcTemplate.update("INSERT INTO Package VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                packet.getPackage_id(),
                packet.getReceiver_id(),
                packet.getSender_id(),
                packet.getDelivery_date(),
                packet.getSend_date(),
                packet.getPayment_side(),
                packet.getPayment_status(),
                packet.getDelivery_type(),
                packet.getStatus(),
                packet.getPackage_type(),
                packet.getCourier(),
                packet.getFrom_city(),
                packet.getCurr_city(),
                packet.getTo_city(),
                packet.getCost());
    }

    private void setPropertiesOfToInsert(Package packet){
        String id = RandomID.generateUUID();
        String receiverId = packet.getReceiver_id().substring(0, packet.getReceiver_id().indexOf('-'));
        String paymentStatus = packet.getPayment_side().equalsIgnoreCase("sender") ? "paid" : "not paid";
        String from = packet.getFrom_city().substring(packet.getFrom_city().indexOf('-') + 1);

        Customer c = customerRepository.searchCustomerFromId(receiverId);
        String to_city = c.getCity_name();

        packet.setPackage_id(id)
                .setReceiver_id(receiverId)
                .setSender_id(currentUser.getID())
                .setSend_date(DatePicker.getDate())
                .setPayment_status(paymentStatus)
                .setStatus("preparing")
                .setCourier(CourierPicker.getRandomCourierName())
                .setFrom_city(from)
                .setCurr_city(from)
                .setTo_city(to_city);
    }
}
