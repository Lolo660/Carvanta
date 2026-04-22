package org.carvanta;


public class Car {

    private int carId;
    private String brand;
    private String model;
    private String category;
    private boolean available;

    public Car(int carId, String brand, String model, String category) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.available = true;
    }

    public int getCarId() {
        return carId;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        available = false;
    }

    public void giveBack() {
        available = true;
    }

    public void displayCar() {
        System.out.println(carId + " - " + brand + " " + model + " (" + category + ")");
    }
}