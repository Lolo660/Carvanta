package org.carvanta;

import java.util.*;

public class CarRentalSystem {

    // I used a List because the system can have many cars.
    // A List lets me store multiple cars and go through them easily when needed.
    private List<Car> cars = new ArrayList<>();

    // I used a Map to organize cars by their category.
    // Each category (like SUV or Electric) is linked to a list of cars in that category.
    // This makes it easy to find cars based on what the user wants.
    private Map<String, List<Car>> carsByCategory = new HashMap<>();

    // I used a Set to store categories so that each category appears only once.
    // Even if I add many cars of the same category, it won’t repeat.
    private Set<String> categories = new HashSet<>();



    public void addCar(Car car) {

        cars.add(car);

        categories.add(car.getCategory());

        carsByCategory.putIfAbsent(car.getCategory(), new ArrayList<>());
        carsByCategory.get(car.getCategory()).add(car);
    }


    public void showCategories() {

        System.out.println("\nAvailable Categories:");

        for (String category : categories) {
            System.out.println("- " + category);
        }
    }


    public void showCarsByCategory(String category) {

        List<Car> categoryCars = carsByCategory.get(category);

        if (categoryCars == null) {
            System.out.println("No such category!");
            return;
        }

        System.out.println("\nCars in " + category + ":");

        for (Car car : categoryCars) {
            if (car.isAvailable()) {
                car.displayCar();
            }
        }
    }
    public void removeCar(int carId) {

        Car carToRemove = null;

        for (Car car : cars) {
            if (car.getCarId() == carId) {
                carToRemove = car;
                break;
            }
        }

        if (carToRemove != null) {

            cars.remove(carToRemove);

            carsByCategory.get(carToRemove.getCategory()).remove(carToRemove);

            System.out.println("Car removed successfully.");
        } else {
            System.out.println("Car not found.");
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
            throw new CarNotAvailableException("Car already rented!");
        }

        car.rent();
        System.out.println("Car rented successfully!");
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