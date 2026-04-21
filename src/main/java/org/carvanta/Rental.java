package org.carvanta;

public class Rental {

    private Customer customer;
    private Car car;
    private int days;

    public Rental(Customer customer, Car car, int days) {
        this.customer = customer;
        this.car = car;
        this.days = days;
    }

    public void showDetails() {
        System.out.println(customer.name + " rented a car for " + days + " days.");
    }
}