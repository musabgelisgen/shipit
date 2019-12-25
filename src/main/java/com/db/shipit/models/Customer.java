package com.db.shipit.models;

public class Customer extends User{

    private String city;
    private int credits;
    private int phoneNumber;
    private String address;

    public Customer (){}

    public Customer(String ID, String email, String encryptedPassword, String firstName, String lastName,
                    String city, int credits, int phoneNumber, String address) {
        super(ID, email, encryptedPassword, firstName, lastName);
        this.city = city;
        this.credits = credits;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer(String city, int credits) {
        this.city = city;
        this.credits = credits;
    }

    public String getCity() {
        return city;
    }

    public Customer setCity(String city) {
        this.city = city;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Customer setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public Customer setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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
                "city='" + city + '\'' +
                ", credits=" + credits +
                ", phoneNumber=" + phoneNumber +
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
        if (phoneNumber != customer.phoneNumber) return false;
        if (city != null ? !city.equals(customer.city) : customer.city != null) return false;
        return address != null ? address.equals(customer.address) : customer.address == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + credits;
        result = 31 * result + phoneNumber;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

