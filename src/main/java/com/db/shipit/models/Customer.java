package com.db.shipit.models;

public class Customer extends User{

    private String city_name;
    private int credits;
    private int phone_number;
    private String address;

    public Customer (){}

    public Customer(String ID, String email, String encryptedPassword, String firstName, String lastName,
                    String city_name, int credits, int phone_number, String address) {
        super(ID, email, encryptedPassword, firstName, lastName);
        this.city_name = city_name;
        this.credits = credits;
        this.phone_number = phone_number;
        this.address = address;
    }

    public Customer(String city_name, int credits) {
        this.city_name = city_name;
        this.credits = credits;
    }

    public String getCity_name() {
        return city_name;
    }

    public Customer setCity_name(String city_name) {
        this.city_name = city_name;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Customer setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public Customer setPhone_number(int phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public String getIdAndFullName(){
        return getID() + "-" + getFullName();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "city='" + city_name + '\'' +
                ", credits=" + credits +
                ", phoneNumber=" + phone_number +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        if (credits != customer.credits) return false;
        if (phone_number != customer.phone_number) return false;
        if (city_name != null ? !city_name.equals(customer.city_name) : customer.city_name != null) return false;
        return address != null ? address.equals(customer.address) : customer.address == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city_name != null ? city_name.hashCode() : 0);
        result = 31 * result + credits;
        result = 31 * result + phone_number;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

