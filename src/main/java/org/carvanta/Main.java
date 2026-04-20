package org.carvanta;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CarRentalSystem system = new CarRentalSystem();

        // ✅ 8 CARS UPDATED
        system.setCars(
                new Car(1, "Toyota Corolla"),
                new Car(2, "BMW X5"),
                new Car(3, "Mercedes-Benz C-Class"),
                new Car(4, "Honda Civic"),
                new Car(5, "Audi A4"),
                new Car(6, "Hyundai Tucson"),
                new Car(7, "Range Rover Evoque"),
                new Car(8, "Volkswagen Golf")
        );

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        Customer customer = new Customer(1, name);
        customer.displayRole();

        int choice;

        do {
            System.out.println("\n1. View Cars");
            System.out.println("2. Rent Car");
            System.out.println("3. Return Car");
            System.out.println("4. Exit");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    system.showAvailableCars();
                    break;

                case 2:
                    System.out.print("Enter Car ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter Days: ");
                    int days = scanner.nextInt();
                    system.rentCar(customer, id, days);
                    break;

                case 3:
                    System.out.print("Enter Car ID: ");
                    int rid = scanner.nextInt();
                    system.returnCar(rid);
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;
            }

        } while (choice != 4);

        scanner.close();
    }
}