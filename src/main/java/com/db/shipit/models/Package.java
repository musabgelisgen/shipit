package com.db.shipit.models;

public class Package {
    String package_id;
    String receiver_id;
    String sender_id;
    String delivery_date;
    String send_date;
    String payment_side;
    String payment_status;
    String delivery_type;
    String status;
    String package_type;
    String courier;
    String from_city;
    String curr_city;
    String to_city;
    double cost;

    public String getPackage_id() {
        return package_id;
    }

    public Package setPackage_id(String package_id) {
        this.package_id = package_id;
        return this;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public Package setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
        return this;
    }

    public String getSender_id() {
        return sender_id;
    }

    public Package setSender_id(String sender_id) {
        this.sender_id = sender_id;
        return this;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public Package setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
        return this;
    }

    public String getSend_date() {
        return send_date;
    }

    public Package setSend_date(String send_date) {
        this.send_date = send_date;
        return this;
    }

    public String getPayment_side() {
        return payment_side;
    }

    public Package setPayment_side(String payment_side) {
        this.payment_side = payment_side.toLowerCase();
        return this;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public Package setPayment_status(String payment_status) {
        this.payment_status = payment_status.toLowerCase();
        return this;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public Package setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type.toLowerCase();
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Package setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPackage_type() {
        return package_type;
    }

    public Package setPackage_type(String package_type) {
        this.package_type = package_type.toLowerCase();
        return this;
    }

    public String getCourier() {
        return courier;
    }

    public Package setCourier(String courier) {
        this.courier = courier;
        return this;
    }

    public String getFrom_city() {
        return from_city;
    }

    public Package setFrom_city(String from_city) {
        this.from_city = from_city;
        return this;
    }

    public String getCurr_city() {
        return curr_city;
    }

    public Package setCurr_city(String curr_city) {
        this.curr_city = curr_city;
        return this;
    }

    public String getTo_city() {
        return to_city;
    }

    public Package setTo_city(String to_city) {
        this.to_city = to_city;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public Package setCost(double cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public String toString() {
        return "Package{" +
                "package_id='" + package_id + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", delivery_date=" + delivery_date +
                ", send_date=" + send_date +
                ", payment_side='" + payment_side + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", delivery_type='" + delivery_type + '\'' +
                ", status='" + status + '\'' +
                ", package_type='" + package_type + '\'' +
                ", courier='" + courier + '\'' +
                ", from_city='" + from_city + '\'' +
                ", curr_city='" + curr_city + '\'' +
                ", to_city='" + to_city + '\'' +
                ", cost=" + cost +
                '}';
    }
}
