package com.db.shipit.models;

public class Message {
    private String report_id;
    private String message_number;
    private String  text;
    private String send_date;
    private String sender_id;

    public Message(){}
    public Message(String report_id, String message_number, String text, String send_date, String sender_id) {
        this.report_id = report_id;
        this.message_number = message_number;
        this.text = text;
        this.send_date = send_date;
        this.sender_id = sender_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }


    public String getMessage_number() {
        return message_number;
    }

    public void setMessage_number(String message_number) {
        this.message_number = message_number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    @Override
    public String toString() {
        return "Package{" +
                "report_id='" + report_id + '\'' +
                ", message_number='" + message_number + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ",text=" + text +
                ", send_date=" + send_date +
                '}';
    }
}
