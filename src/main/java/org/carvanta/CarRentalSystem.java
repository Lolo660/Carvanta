package org.carvanta;

import java.util.ArrayList;

public class CarRentalSystem {

    private ArrayList<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void showCarsByCategory(String category) {

        System.out.println("\nAvailable " + category + " Cars:");

        for (Car car : cars) {
            if (car.isAvailable() &&
                    car.getCategory().equalsIgnoreCase(category)) {
                car.displayCar();
            }
        }
    }

    private Car findCar(int id) {
        for (Car car : cars) {
            if (car.getCarId() == id) {
                return car;
            }
        }
        return null;
    }

    public void rentCar(Customer customer, int carId, int days)
            throws CarNotAvailableException {

        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than 0");
        }

        Car car = findCar(carId);

        if (car == null) {
            throw new IllegalArgumentException("Invalid Car ID");
        }

        if (!car.isAvailable()) {
            throw new CarNotAvailableException("Car is already rented!");
        }

        car.rent();

        System.out.println("Car rented successfully by " + customer.name);
    }


    public void returnCar(int carId) {

        Car car = findCar(carId);

        if (car == null) {
            throw new IllegalArgumentException("Invalid Car ID");
        }

        car.giveBack();

        System.out.println("Car returned successfully!");
    }
}