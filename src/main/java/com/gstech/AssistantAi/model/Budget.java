package com.gstech.AssistantAi.model;

public class Budget {

    private int adults;
    private int childrenUnder6;
    private int childrenUnder12;

    public Budget(int adults, int childrenUnder6, int childrenUnder12) {
        this.adults = adults;
        this.childrenUnder6 = childrenUnder6;
        this.childrenUnder12 = childrenUnder12;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildrenUnder6() {
        return childrenUnder6;
    }

    public void setChildrenUnder6(int childrenUnder6) {
        this.childrenUnder6 = childrenUnder6;
    }

    public int getChildrenUnder12() {
        return childrenUnder12;
    }

    public void setChildrenUnder12(int childrenUnder12) {
        this.childrenUnder12 = childrenUnder12;
    }

    public double calculateMeatKg() {

        double adultConsumption = 0.5;
        double childUnder6Consumption = 0.15;
        double childUnder12Consumption = 0.25;

        return (adults * adultConsumption) +
                (childrenUnder6 * childUnder6Consumption) +
                (childrenUnder12 * childUnder12Consumption);
    }
}
