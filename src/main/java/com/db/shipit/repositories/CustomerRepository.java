package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(){
        //bun 3u denemelik sadece.
        jdbcTemplate.update("insert into Branch values (?,?)", "Kadikoy", "Istanbul");
        jdbcTemplate.update("insert into User values (?,?,?,?,?)", "1", "asda@gmail.com", "pass", "first_name", "last_name");
        jdbcTemplate.update("insert into Customer values (?,?,?,?,?)", "1", "Ankara", "40", "asd", 0);
    }
}
