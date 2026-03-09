package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.Buffet;
import com.gstech.AssistantAi.service.utils.BudgetCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BudgetService {

    private final BudgetCalculator calculator;

    public BudgetService(BudgetCalculator calculator) {
        this.calculator = calculator;
    }

    // calculo do  buffet
    public BigDecimal calculateBuffet(Buffet type, int adults, boolean includeChildrenUnder6, boolean includeChildrenUnder12,
                                      int childrenUnder6, int childrenUnder12, int eventDurationHours) {

        double sumGuests;
        double operationalCost = 1.15;
        double totalChildrenUnder6 = 0;
        double totalChildrenUnder12 = 0;

        if (includeChildrenUnder6) {

            totalChildrenUnder6 = childrenUnder6 * 0.25;
        }

        if (includeChildrenUnder12) {

            totalChildrenUnder12 = childrenUnder12 * 0.50;
        }

        sumGuests = adults + totalChildrenUnder6 + totalChildrenUnder12;
        int sumTotalGuests = (int) Math.ceil(sumGuests);

        return calculator.calculateBuffetSelection(type, (int) sumTotalGuests, eventDurationHours)
                .multiply(BigDecimal.valueOf(operationalCost));
    }


    // calculo de cerveja
    public BigDecimal calculateBeer(int adults, boolean includeBeer, boolean includeBrahma, boolean includeHeineken, boolean includeSkol,
                                    int quantityBrahma600ml, int quantityHeineken600ml, int quantitySkol600ml) {


        BigDecimal totalCost = BigDecimal.ZERO;

        if (includeBeer) {
            totalCost = totalCost.add(calculator.calculateBeerSelection(adults, includeBrahma, includeHeineken, includeSkol, quantityBrahma600ml
                    , quantityHeineken600ml, quantitySkol600ml));
        }

        return totalCost;
    }

    // calculo de suco
    public BigDecimal calculateJuice(int adults, int children, boolean includeJuice, boolean includeLaranja, boolean includeMaracuja,
                                     boolean includeAbacaxi, int quantityLaranja, int quantityMaracuja, int quantityAbacaxi) {

        BigDecimal totalCost = BigDecimal.ZERO;

        if (includeJuice) {
            totalCost = totalCost.add(calculator.calculateJuiceSelection(adults, children, includeLaranja, includeMaracuja,
                    includeAbacaxi, quantityLaranja, quantityMaracuja, quantityAbacaxi));
        }

        return totalCost;
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
