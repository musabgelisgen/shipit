package com.db.shipit.models;

public class Subscription {
    private String ID;
    private int subscription_number;
    private int subscription_tier;
    private int used_package_rights;
    private String start_date;
    private String end_date;
    private boolean is_active;

    public Subscription(String ID, int subscription_number, int subscription_tier, int used_package_rights, String start_date, String end_date, boolean is_active){
        this.ID = ID;
        this.subscription_number = subscription_number;
        this.subscription_tier = subscription_tier;
        this.used_package_rights = used_package_rights;
        this.start_date = start_date;
        this.end_date = end_date;
        this.is_active = is_active;
    }

    public Subscription(){};

    public String getID(){ return ID; }

    public Subscription setID(String ID){
        this.ID = ID;
        return this;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public Subscription setIs_active(boolean is_active) {
        this.is_active = is_active;
        return this;
    }

    public int getSubscriptionNumber(){ return subscription_number; }

    public Subscription setSubscriptionNumber(int subscription_number){
        this.subscription_number = subscription_number;
        return this;
    }

    public int getSubscriptionTier(){return subscription_tier;}

    public Subscription setSubscriptionTier(int subscription_tier){
        this.subscription_tier = subscription_tier;
        return this;
    }

    public int getUsedPackageRights(){return used_package_rights;}

    public Subscription setUsedPackageRights(int used_package_rights){
        this.used_package_rights = used_package_rights;
        return this;
    }

    public String getStartDate(){return start_date;}

    public Subscription setStartDate(String start_date){
        this.start_date = start_date;
        return this;
    }

    public String getEndDate(){return end_date;}

    public Subscription setEndDate(String end_date){
        this.end_date = end_date;
        return this;
    }
}
