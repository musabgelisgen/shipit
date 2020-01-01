package com.db.shipit.repositories;

import com.db.shipit.models.Package;
import com.db.shipit.models.Report;
import com.db.shipit.utils.DatePicker;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;


@Repository
public class ReportRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Report saveReport(Report report) {
        String handler_id = report.getHandler_id();
        String issuer_id = report.getIssuer_id();
        String package_id = report.getPackage_id();
        String description = report.getDescription();
        String report_type = report.getReport_type();
        String result = report.getResult();

        String id = RandomID.generateUUID();
        report.setReport_id(id);

        String report_status = "waiting";

        String date = DatePicker.getDate();
        jdbcTemplate.update("insert into Report (report_id, handler_id, issuer_id, package_id, description, report_type, report_status, issue_date, result) values (?,?,?,?,?,?,?,?,?)", id, handler_id, issuer_id, package_id, description, report_type, report_status, date, result);
        return report;
    }

    public Report getReport(String issuer_id){
        List<Report> r = jdbcTemplate.query("SELECT * FROM Report WHERE issuer_id = ?",new Object[]{issuer_id},new BeanPropertyRowMapper(Report.class) );
        return r.size() > 0 ? r.get(0) : null;
    }

    public List<Report> getAllReports() {
        String id = currentUser.getID();
        List<Report> packages = jdbcTemplate.query("SELECT * FROM Report WHERE issuer_id = ?", new Object[]{id},new BeanPropertyRowMapper(Report.class));
        return packages;
    }
}
