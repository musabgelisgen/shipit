package com.db.shipit.repositories;

import com.db.shipit.models.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BranchRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Branch> getAllBranches() {
        List<Branch> branches = jdbcTemplate.query("SELECT * FROM Branch", new BeanPropertyRowMapper(Branch.class));
        return branches;
    }
}
