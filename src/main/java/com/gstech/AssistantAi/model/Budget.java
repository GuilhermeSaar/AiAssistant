package com.gstech.AssistantAi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Budget {

    private int adults;
    private int childrenUnder6;
    private int childrenUnder12;

    public double calculateMeatKg() {

        double adultConsumption = 0.5;
        double childUnder6Consumption = 0.15;
        double childUnder12Consumption = 0.25;

        double total = (adults * adultConsumption) +
                (childrenUnder6 * childUnder6Consumption) +
                (childrenUnder12 * childUnder12Consumption);

        return total * 1.15; // margem
    }
}
