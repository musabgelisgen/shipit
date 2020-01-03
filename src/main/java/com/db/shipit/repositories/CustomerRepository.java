package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.utils.PasswordEncryption;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveCustomer(Customer customer) {
        String id = RandomID.generateUUID();
        String encr_pass = PasswordEncryption.encryptPasswordUsingSHA256(customer.getEncryptedPassword());

        jdbcTemplate.update("insert into User values (?,?,?,?,?)", id, customer.getEmail(), encr_pass, customer.getFirstName(), customer.getLastName());
        jdbcTemplate.update("insert into Customer values (?,?,?,?,?)", id, customer.getCity_name(), customer.getPhone_number(), customer.getAddress(), customer.getCredits());
    }

    public Customer searchCustomerFromId(String id){
        List<Customer> c = jdbcTemplate.query("SELECT * FROM Customer NATURAL JOIN UserAbstraction WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper(Customer.class));
        return c.size() > 0 ? c.get(0) : null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM Customer NATURAL JOIN UserAbstraction", new BeanPropertyRowMapper(Customer.class));
        return customers;
    }

    public boolean changeCustomerBalance(String customerID, int amount){
        Customer customer = searchCustomerFromId(customerID);
        if(customer.getCredits() + amount >= 0) {
            jdbcTemplate.update("UPDATE Customer SET credits = credits + ? WHERE ID = ?", amount, currentUser.getID());
            return true;
        }
        return false;
    }

    public int getCustomerBalance(){
        Customer customer = searchCustomerFromId(currentUser.getID());
        int balance = customer.getCredits();
        return balance;
    }
}
