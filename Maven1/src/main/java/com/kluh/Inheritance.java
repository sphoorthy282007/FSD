package com.kluh;

class Vehicle {
    void vehicleType() {
        System.out.println("This is a vehicle");
    }
}

class Car extends Vehicle {
    void carBrand() {
        System.out.println("This is a car");
    }
}

class ElectricCar extends Car {
    void batteryStatus() {
        System.out.println("This is an electric car with a charged battery");
    }
}

public class Inheritance {

    public static void main(String[] args) {
        ElectricCar ec = new ElectricCar();

        ec.vehicleType();
        ec.carBrand();
        ec.batteryStatus();
    }
}
