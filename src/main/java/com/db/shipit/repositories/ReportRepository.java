package com.db.shipit.repositories;

import com.db.shipit.models.Message;
import com.db.shipit.models.Report;
import com.db.shipit.utils.DatePicker;
import com.db.shipit.utils.RandomID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;
import static com.db.shipit.ShipitApplication.currentUser;


@Repository
public class ReportRepository {
    public ReportRepository() //No parameters makes this the default
    {
    }
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

  /*  public Report getReport(String issuer_id){
        List<Report> r = jdbcTemplate.query("SELECT * FROM Report WHERE issuer_id = ?",new Object[]{issuer_id},new BeanPropertyRowMapper(Report.class) );
        return r.size() > 0 ? r.get(0) : null;
    }*/
    public Report getReportByID(String report_id){
        List<Report> r = jdbcTemplate.query("SELECT * FROM Report WHERE report_id = ?",new Object[]{report_id},new BeanPropertyRowMapper(Report.class) );

        return r.size() > 0 ? r.get(0) : null;
    }
        public void commitMessage(Message message,String report_id) {
        setPropertiesOfToInsert(message,report_id);

        jdbcTemplate.update("INSERT INTO Message VALUES (?,?,?,?,?)",
                 message.getReport_id(),
                message.getMessage_number(),
                message.getText(),
                message.getSend_date(),
                message.getSender_id()
        );
    }

    public List<String> getAllMessages(String report_id) {
        List<String> messageTexts =new ArrayList<>()  ;

        if(!report_id.equals(NULL)){
            List<Message> messages = jdbcTemplate.query("SELECT * FROM Message WHERE report_id = ?", new Object[]{report_id},new BeanPropertyRowMapper(Message.class));
            for (Message message : messages) messageTexts.add(message.getText());
        }

        return messageTexts;
    }
    private void setPropertiesOfToInsert(Message message,String report_id1){
        //String report_id= report_id1;

        List<String> messageTexts =new ArrayList<>()  ;
        List<Message> messages = jdbcTemplate.query("SELECT * FROM Message WHERE report_id = ?", new Object[]{report_id1},new BeanPropertyRowMapper(Message.class));
        int max =-1;
        if (messages.size()==0)
            max=0;
        for (Message messageO : messages) {
                int temp =Integer.parseInt(messageO.getMessage_number());
            if(temp>max)
                max=temp;
        }
        message.setMessage_number(String.valueOf(max+1));
        message.setReport_id(report_id1);
        message.setSender_id(currentUser.getID());
        message.setSend_date(DatePicker.getDate());
        message.setText(message.getText());
    }

    public List<Report> getAllReports() {
        String id = currentUser.getID();
        List<Report> reports = jdbcTemplate.query("SELECT * FROM Report WHERE issuer_id = ?", new Object[]{id},new BeanPropertyRowMapper(Report.class));
        return reports;
    }
}
