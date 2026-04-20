package org.carvanta;

public class Car {

    private int carId;
    private String model;
    private boolean available;

    public Car(int carId, String model) {
        this.carId = carId;
        this.model = model;
        this.available = true;
    }

    public int getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
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
}