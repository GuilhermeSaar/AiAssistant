package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BudgetCalculationService {

    private final DrinkRepository repository;

    public BudgetCalculationService(DrinkRepository repository) {
        this.repository = repository;
    }

     //taxa de hora extra para eventos com duracao maior que 4 horas
    public double hourlyRate(int eventDurationHours) {
        int extraHours = eventDurationHours - 4;

        if (extraHours <= 0) {
            return 1.0;
        }
        return 1.0 + (extraHours * 0.10);
    }

    // calculo do churrasco
    public BigDecimal calculateBBQ(BBQ type, int adults, int childrenUnder12, int eventDurationHours) {

        double sumGuests;
        double operationalCost = 1.15;
        double totalChildrenUnder12 = 0;
        BigDecimal totalCostBBQ = BigDecimal.ZERO;


        if (childrenUnder12 > 0) {

            totalChildrenUnder12 = childrenUnder12 * 0.50;
        }

        sumGuests = adults + totalChildrenUnder12;
        int sumTotalGuests = (int) Math.ceil(sumGuests);

        if (BBQ.CHURRASCO_PREMIUM == type) {
            totalCostBBQ = BigDecimal.valueOf(79.90).multiply(BigDecimal.valueOf(sumTotalGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        else if (BBQ.CHURRASCO_ESSENCIAL == type) {
            totalCostBBQ = BigDecimal.valueOf(59.90).multiply(BigDecimal.valueOf(sumTotalGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        return totalCostBBQ.multiply(BigDecimal.valueOf(operationalCost)).setScale(2, RoundingMode.HALF_UP);
    }

    // calculo de cerveja
    public BigDecimal calculateBeer(int quantityBrahma600ml, int quantityHeineken600ml, int quantitySkol600ml) {

        BigDecimal totalCostBeer = BigDecimal.ZERO;
        double taxBeer = 1.25;

        if (quantityBrahma600ml > 0) {
            totalCostBeer = totalCostBeer.add(BigDecimal.valueOf(quantityBrahma600ml)
            .multiply(repository.findPriceByNameDrink(NameDrink.BRAHMA)));
        }

        if (quantityHeineken600ml > 0) {
            totalCostBeer = totalCostBeer.add(BigDecimal.valueOf(quantityHeineken600ml)
                    .multiply(repository.findPriceByNameDrink(NameDrink.HEINEKEN)));
        }

        if (quantitySkol600ml > 0) {
            totalCostBeer = totalCostBeer.add(BigDecimal.valueOf(quantitySkol600ml)
                    .multiply(repository.findPriceByNameDrink(NameDrink.SKOL)));
        }

        return totalCostBeer.multiply(BigDecimal.valueOf(taxBeer).setScale(2, RoundingMode.HALF_UP));
    }

    // calculo de suco
    public BigDecimal calculateJuice(int quantityLaranja, int quantityMaracuja, int quantityAbacaxi) {

        BigDecimal totalCost = BigDecimal.ZERO;
        double taxJuice = 1.10;

        if (quantityLaranja > 0) {
            totalCost = totalCost.add(BigDecimal.valueOf(quantityLaranja)
                    .multiply(repository.findPriceByNameDrink(NameDrink.LARANJA)));
        }

        if (quantityMaracuja > 0) {
            totalCost = totalCost.add(BigDecimal.valueOf(quantityMaracuja)
                    .multiply(repository.findPriceByNameDrink(NameDrink.MARACUJA)));
        }

        if (quantityAbacaxi > 0) {
            totalCost = totalCost.add(BigDecimal.valueOf(quantityAbacaxi)
                    .multiply(repository.findPriceByNameDrink(NameDrink.ABACAXI)));
        }

        return totalCost.multiply(BigDecimal.valueOf(taxJuice).setScale(2, RoundingMode.HALF_UP));
    }

    // calculo total
    public BigDecimal calcTotalBudget(BigDecimal totalBuffet, BigDecimal totalBeer, BigDecimal totalJuice) {

        totalBuffet = totalBuffet == null ? BigDecimal.ZERO : totalBuffet;
        totalBeer = totalBeer == null ? BigDecimal.ZERO : totalBeer;
        totalJuice = totalJuice == null ? BigDecimal.ZERO : totalJuice;

        return totalBuffet
                .add(totalBeer)
                .add(totalJuice)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
