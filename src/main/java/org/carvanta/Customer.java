package org.carvanta;

public class Customer extends Person {

    public Customer(int id, String name) {
        super(id, name);
    }

    @Override
    public void displayRole() {
        System.out.println("Customer: " + name);
    }
}