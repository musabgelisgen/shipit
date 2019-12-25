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
public class CustomerServiceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveCustomerService(CustomerService customerService) {
        String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        String encr_pass = PasswordEncryption.encryptPasswordUsingSHA256(customerService.getEncryptedPassword());

        jdbcTemplate.update("insert into User values (?,?,?,?,?)", id, customerService.getEmail(), encr_pass, customerService.getFirstName(), customerService.getLastName());
        jdbcTemplate.update("insert into CustomerService values (?,?,?)", id, customerService.getBranchName(), customerService.getSalary());
    }

}
