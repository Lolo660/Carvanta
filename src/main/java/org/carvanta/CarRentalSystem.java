package org.carvanta;

public class CarRentalSystem {

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    private Car car5;
    private Car car6;
    private Car car7;
    private Car car8;

    public void setCars(
            Car c1, Car c2, Car c3, Car c4,
            Car c5, Car c6, Car c7, Car c8
    ) {
        this.car1 = c1;
        this.car2 = c2;
        this.car3 = c3;
        this.car4 = c4;
        this.car5 = c5;
        this.car6 = c6;
        this.car7 = c7;
        this.car8 = c8;
    }

    public void showAvailableCars() {
        System.out.println("\nAvailable Cars:");

        display(car1);
        display(car2);
        display(car3);
        display(car4);
        display(car5);
        display(car6);
        display(car7);
        display(car8);
    }

    private void display(Car car) {
        if (car != null && car.isAvailable()) {
            System.out.println(car.getCarId() + " - " + car.getModel());
        }
    }

    public void rentCar(Customer customer, int carId, int days) {

        Car selected = findCar(carId);

        if (selected != null && selected.isAvailable()) {
            selected.rent();
            System.out.println("Car rented successfully!");
        } else {
            System.out.println("Car not available.");
        }
    }

    public void returnCar(int carId) {

        Car car = findCar(carId);

        if (car != null) {
            car.giveBack();
            System.out.println("Car returned successfully!");
        }
    }

    private Car findCar(int id) {

        if (car1.getCarId() == id) return car1;
        if (car2.getCarId() == id) return car2;
        if (car3.getCarId() == id) return car3;
        if (car4.getCarId() == id) return car4;
        if (car5.getCarId() == id) return car5;
        if (car6.getCarId() == id) return car6;
        if (car7.getCarId() == id) return car7;
        if (car8.getCarId() == id) return car8;

        return null;
    }
}