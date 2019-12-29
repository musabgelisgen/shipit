package com.db.shipit.repositories;

import com.db.shipit.models.Customer;
import com.db.shipit.models.CustomerService;
import com.db.shipit.models.Package;
import com.db.shipit.models.Report;
import com.db.shipit.utils.PasswordEncryption;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import com.db.shipit.utils.RandomID;

@Repository
public class ReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveReport(Report report) {
        String handler_id = report.getHandler_id();
        String issuer_id = report.getIssuer_id();
        String package_id = report.getPackage_id();
        String description = report.getDescription();
        String report_type = report.getReport_type();
        String result = report.getResult();

        String id = RandomID.generateUUID();


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update("insert into Report values (?,?,?,?,?,?,?,?)", id, handler_id, issuer_id, package_id, description, report_type, dtf.format(now), result);
    }

    public List<Report> getReport(String issuer_id){
        List<Report> r = jdbcTemplate.query("SELECT * FROM Report WHERE issuer_id = ?",new Object[]{issuer_id},new BeanPropertyRowMapper(Report.class) );
        return r;
        //return r.size() > 0 ? r.get(0) : null;
    }
}
