package com.db.shipit.repositories;

import com.db.shipit.models.Package;
import com.db.shipit.models.User;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PackageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Package> getAllPackages(User user, Map<String, Integer> modifications) {
        String id = user.getID();

        List<Package> packages = jdbcTemplate.query("SELECT * FROM Package WHERE receiver_id = ? OR sender_id = ?", new Object[]{id, id},new BeanPropertyRowMapper(Package.class));
        return packages;
    }

    public void commitPackage(Package packet) {
        String id = RandomID.generateUUID();
    }
}
