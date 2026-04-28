package org.carvanta;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CarRentalSystem system = new CarRentalSystem("cars-data.txt");

        Customer customer = new Customer(1, "User");

        boolean loadedFromFile = system.loadData();

        if (!loadedFromFile) {
            system.addCar(new Car(1, "Toyota", "RAV4", "SUV"));
            system.addCar(new Car(2, "BMW", "X5", "SUV"));
            system.addCar(new Car(3, "Range Rover", "Sport", "SUV"));
            system.addCar(new Car(4, "Mercedes", "GLA", "SUV"));

            system.addCar(new Car(5, "Tesla", "Model 3", "Electric"));
            system.addCar(new Car(6, "Nissan", "Leaf", "Electric"));
            system.addCar(new Car(7, "Hyundai", "Kona", "Electric"));
            system.addCar(new Car(8, "BMW", "i3", "Electric"));

            system.addCar(new Car(9, "Toyota", "Prius", "Hybrid"));
            system.addCar(new Car(10, "Honda", "Insight", "Hybrid"));
            system.addCar(new Car(11, "Ford", "Escape", "Hybrid"));
            system.addCar(new Car(12, "Hyundai", "Ioniq", "Hybrid"));

            system.addCar(new Car(13, "Toyota", "Corolla", "Sedan"));
            system.addCar(new Car(14, "Honda", "Civic", "Sedan"));
            system.addCar(new Car(15, "BMW", "3 Series", "Sedan"));
            system.addCar(new Car(16, "Audi", "A4", "Sedan"));
            system.saveData();
        }

        int choice;

        do {
            System.out.println("\n1. View Categories");
            System.out.println("2. View Cars by Category");
            System.out.println("3. Rent Car");
            System.out.println("4. Return Car");
            System.out.println("5. View Rental History");
            System.out.println("6. Export Rental History to CSV");
            System.out.println("7. Exit");

            choice = scanner.nextInt();

            try {

                switch (choice) {

                    case 1:
                        system.showCategories();
                        break;

                    case 2:
                        System.out.print("Enter category: ");
                        String category = scanner.next();
                        system.showCarsByCategory(category);
                        break;

                    case 3:
                        System.out.print("Enter Car ID: ");
                        int id = scanner.nextInt();

                        System.out.print("Enter Days: ");
                        int days = scanner.nextInt();

                        system.rentCar(customer, id, days);
                        break;

                    case 4:
                        System.out.print("Enter Car ID: ");
                        int rid = scanner.nextInt();
                        system.returnCar(rid);
                        break;

                    case 5:
                        system.displayRentalHistory();
                        break;

                    case 6:
                        System.out.print("Enter CSV file name (e.g., rental-report.csv): ");
                        String csvFileName = scanner.next();
                        system.exportRentalHistoryToCSV(csvFileName);
                        break;
                }

            } catch (CarNotAvailableException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Input Error: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }

        } while (choice != 7);

        scanner.close();
    }
}