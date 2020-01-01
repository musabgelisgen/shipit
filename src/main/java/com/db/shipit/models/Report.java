package com.db.shipit.models;

public class Report {
    String report_id;
    String handler_id;
    String issuer_id;
    String package_id;
    String description;
    String report_type;
    String issue_date;
    String result;
    String report_status;

    public Report(){}

    public Report(String report_id, String handler_id, String issuer_id, String package_id, String description, String report_type, String issue_date, String result, String report_status) {
        this.report_id = report_id;
        this.handler_id = handler_id;
        this.issuer_id = issuer_id;
        this.package_id = package_id;
        this.description = description;
        this.report_type = report_type;
        this.issue_date = issue_date;
        this.result = result;
        this.report_status = report_status;
    }

    public String getReport_status() {
        return report_status;
    }

    public Report setReport_status(String report_status) {
        this.report_status = report_status;
        return this;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getHandler_id() {
        return handler_id;
    }

    public String getIssuer_id() {
        return issuer_id;
    }

    public String getDescription() {
        return description;
    }

    public String getReport_type() {
        return report_type;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public String getResult() {
        return result;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public void setHandler_id(String handler_id) {
        this.handler_id = handler_id;
    }

    public void setIssuer_id(String issuer_id) {
        this.issuer_id = issuer_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }
}
