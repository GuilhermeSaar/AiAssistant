package com.gstech.AssistantAi.service.utils;

import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BudgetCalculator {

    private final DrinkRepository repository;

    public BudgetCalculator(DrinkRepository repository) {
        this.repository = repository;
    }

    // taxa de hora extra para eventos com duracao maior que 4 horas
    public double hourlyRate(int eventDurationHours) {
        int extraHours = eventDurationHours - 4;

        if (extraHours <= 0) {
            return 1.0;
        }
        return 1.0 + (extraHours * 0.10);
    }

    // calculo suco
    public BigDecimal calculateJuice(int adults, int children, int qtdJuice, NameDrink nameJuice) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pricePerLiter = repository.findPriceByNameDrink(nameJuice);

        if (qtdJuice > 0) {
            total = total.add(
                    BigDecimal.valueOf(qtdJuice)
                            .multiply(pricePerLiter));
        } else {

            double qtdLiters = (adults * 0.6) + (children * 0.4);
            int unitsLiters = (int) Math.ceil(qtdLiters);
            total = total.add(
                    BigDecimal.valueOf(unitsLiters)
                            .multiply(pricePerLiter));
        }
        return total;
    }

    public BigDecimal calculateBBQSelection(BBQ nameBuffet, int totalGuests, int eventDurationHours) {

        BigDecimal totalCost = BigDecimal.ZERO;

        if (nameBuffet == BBQ.CHURRASCO_ESSENCIAL) {
            totalCost = BigDecimal.valueOf(59.90).multiply(BigDecimal.valueOf(totalGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        else if (nameBuffet == BBQ.CHURRASCO_PREMIUM) {
            totalCost = BigDecimal.valueOf(79.90).multiply(BigDecimal.valueOf(totalGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    // selecao de sucos escolhidas pelo cliente
    public BigDecimal calculateJuiceSelection(int adults, int children,
            int qtdLaranja,
            int qtdMaracuja, int qtdAbacaxi) {
        BigDecimal sumTotal = BigDecimal.ZERO;

        if (qtdLaranja > 0) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdLaranja, NameDrink.LARANJA));
        }

        if (qtdMaracuja > 0) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdMaracuja, NameDrink.MARACUJA));
        }

        if (qtdAbacaxi > 0) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdAbacaxi, NameDrink.ABACAXI));
        }
        return sumTotal;
    }

    // selecao de cervejas escolhidas pelo cliente
    public BigDecimal calculateBeerSelection(int adults, boolean brahma, boolean heineken, boolean skol,
            int qtdBrahma600ml,
            int qtdHeineken600ml, int qtdSkol600ml) {
        BigDecimal sumTotal = BigDecimal.ZERO;
        int selectedTypes = 0;

        if (brahma)
            selectedTypes++;
        if (heineken)
            selectedTypes++;
        if (skol)
            selectedTypes++;

        if (selectedTypes == 0)
            return sumTotal;

        if (brahma) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdBrahma600ml, NameDrink.BRAHMA, selectedTypes));
        }

        if (heineken) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdHeineken600ml, NameDrink.HEINEKEN, selectedTypes));
        }

        if (skol) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdSkol600ml, NameDrink.SKOL, selectedTypes));
        }
        return sumTotal;
    }

    // calculo cerveja
    public BigDecimal calculateBeer(int adults, int qtdBeer600ml, NameDrink nameDrink, int selectedTypes) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pricePerUnit = repository.findPriceByNameDrink(nameDrink);

        if (qtdBeer600ml > 0) {
            total = total.add(
                    BigDecimal.valueOf(qtdBeer600ml)
                            .multiply(pricePerUnit));

        } else {
            double liters = adults * 1.50;
            int units600ml = (int) Math.ceil((liters / 0.6) / selectedTypes);
            total = total.add(
                    BigDecimal.valueOf(units600ml)
                            .multiply(pricePerUnit));
        }
        return total;
    }
}
