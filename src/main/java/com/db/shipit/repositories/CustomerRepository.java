package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.models.CustomerService;
import com.db.shipit.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveCustomer(Customer customer) {
        String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        String encr_pass = PasswordEncryption.encryptPasswordUsingSHA256(customer.getEncryptedPassword());

        jdbcTemplate.update("insert into User values (?,?,?,?,?)", id, customer.getEmail(), encr_pass, customer.getFirstName(), customer.getLastName());
        jdbcTemplate.update("insert into Customer values (?,?,?,?,?)", id, customer.getCity(), customer.getPhoneNumber(), customer.getAddress(), customer.getCredits());
    }


    public List<Customer> getAllCustomers() {
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM Customer NATURAL JOIN User", new BeanPropertyRowMapper(Customer.class));
        return customers;
    }
}
