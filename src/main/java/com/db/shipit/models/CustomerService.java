package com.db.shipit.models;

public class CustomerService extends User {
    String branchName;
    double salary;

    public String getBranchName() {
        return branchName;
    }

    public CustomerService setBranchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public CustomerService setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    @Override
    public String toString() {
        return "CustomerService{" +
                "branchName='" + branchName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
