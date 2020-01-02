package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.models.CustomerService;
import com.db.shipit.models.User;
import com.db.shipit.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User checkCredentials(User user) {
        String encr_pass = PasswordEncryption.encryptPasswordUsingSHA256(user.getEncryptedPassword());

        List query = jdbcTemplate.query("SELECT * FROM User WHERE email = ? AND encrypted_password = ?",
                new Object[]{user.getEmail(), encr_pass},
                new BeanPropertyRowMapper(User.class));

        if (query.size() == 0)
            return null;
        else {
            String id = ((User) query.get(0)).getID();

            query = jdbcTemplate.query("SELECT * FROM Customer WHERE ID = ?",
                    new Object[]{id},
                    new BeanPropertyRowMapper(Customer.class));
            if (query.size() != 0){
                return (Customer) query.get(0);
            }
            else{
                query = jdbcTemplate.query("SELECT * FROM CustomerService WHERE ID = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper(CustomerService.class));

                return (CustomerService) query.get(0);
            }
        }
    }

    public User searchUserFromId(String id){
        List<User> c = jdbcTemplate.query("SELECT * FROM User WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper(User.class));
        return c.size() > 0 ? c.get(0) : null;
    }
}
