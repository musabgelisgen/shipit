package com.db.shipit.models;

public class Subscription {
    private String ID;
    private int subscription_tier;
    private int used_package_rights;
    private String start_date;
    private String end_date;

    public Subscription(String ID, int subscription_tier, int used_package_rights, String start_date, String end_date){
        this.ID = ID;
        this.subscription_tier = subscription_tier;
        this.used_package_rights = used_package_rights;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Subscription(){};

    public String getID(){ return ID; }

    public Subscription setID(String ID){
        this.ID = ID;
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
