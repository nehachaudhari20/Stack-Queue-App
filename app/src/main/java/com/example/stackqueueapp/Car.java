package com.example.stackqueueapp;

public class Car {
    String numberPlate;
    boolean hasFastTag;
    int numWheels;

    public Car(String numberPlate, boolean hasFastTag, int numWheels) {
        this.numberPlate = numberPlate;
        this.hasFastTag = hasFastTag;
        this.numWheels = numWheels;
    }

    public double calculateToll() {
        double tollRate = 50.0; // Example toll rate
        return tollRate * numWheels; // Calculate toll based on the number of wheels
    }
}
