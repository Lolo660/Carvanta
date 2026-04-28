package org.carvanta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CarRentalSystem {


    private List<Car> cars = new ArrayList<>();


    private Map<String, List<Car>> carsByCategory = new HashMap<>();


    private Set<String> categories = new HashSet<>();
    private final File dataFile;
    private final RentalLogger rentalLogger;

    public CarRentalSystem(String dataFile) {
        this.dataFile = new File(dataFile);
        this.rentalLogger = new RentalLogger("rental-history.txt");
    }


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
            saveData();
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
        rentalLogger.logRentalTransaction(customer, car, "RENTED", days);
        System.out.println("Car rented successfully!");
        saveData();
    }


    public void returnCar(int carId) {

        Car car = findCar(carId);

        if (car == null) {
            throw new IllegalArgumentException("Invalid Car ID");
        }

        car.giveBack();
        rentalLogger.logRentalTransaction(new Customer(0, "Unknown"), car, "RETURNED", 0);
        System.out.println("Car returned successfully!");
        saveData();
    }

    public boolean loadData() {
        if (!dataFile.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            cars.clear();
            carsByCategory.clear();
            categories.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 5) {
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                String brand = parts[1];
                String model = parts[2];
                String category = parts[3];
                boolean available = Boolean.parseBoolean(parts[4]);

                addCarWithoutSaving(new Car(id, brand, model, category, available));
            }

            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read cars from file.", e);
        }
    }

    public void saveData() {
        List<String> lines = new ArrayList<>();

        for (Car car : cars) {
            String line = car.getCarId() + "|" + car.getBrand() + "|" + car.getModel() + "|" + car.getCategory() + "|" + car.isAvailable();
            lines.add(line);
        }

        try {
            if (!dataFile.exists() && !dataFile.createNewFile()) {
                throw new IOException("Unable to create data file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare data file.", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save cars to file.", e);
        }
    }

    private void addCarWithoutSaving(Car car) {
        cars.add(car);
        categories.add(car.getCategory());
        carsByCategory.putIfAbsent(car.getCategory(), new ArrayList<>());
        carsByCategory.get(car.getCategory()).add(car);
    }

    public void displayRentalHistory() {
        rentalLogger.displayRentalHistory();
    }

    public void exportRentalHistoryToCSV(String csvFileName) {
        rentalLogger.exportToCSV(csvFileName);
    }

    public void clearRentalHistory() {
        rentalLogger.clearHistory();
    }
}