package com.db.shipit.models;

public class Message {
    private String report_id;
    private String message_number;
    private String  text;
    private String date;
    private String sender;

    public Message(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Message(String report_id, String message_number, String text, String date, String sender) {
        this.report_id = report_id;
        this.message_number = message_number;
        this.text = text;
        this.date = date;
        this.sender = sender;
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


    @Override
    public String toString() {
        return "Package{" +
                "report_id='" + report_id + '\'' +
                ", message_number='" + message_number + '\'' +
                ", sender='" + sender + '\'' +
                ",text=" + text +
                ", date=" + date +
                '}';
    }
}
