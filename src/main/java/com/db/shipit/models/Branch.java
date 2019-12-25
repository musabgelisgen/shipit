package com.db.shipit.models;

public class Branch {
    private String name;
    private String city_name;

    public String getBranchAndCityName(){
        return getName() + "-" + getCity_name();
    }

    public String getName() {
        return name;
    }

    public Branch setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity_name() {
        return city_name;
    }

    public Branch setCity_name(String city_name) {
        this.city_name = city_name;
        return this;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", city_name='" + city_name + '\'' +
                '}';
    }
}
