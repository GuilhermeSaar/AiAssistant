package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.BbqMenuRepository;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BudgetCalculationService {

    private static final int BASE_EVENT_HOURS = 4;
    private static final BigDecimal CHILD_RATE = new BigDecimal("0.50");
    private static final BigDecimal EXTRA_HOUR_RATE = new BigDecimal("0.10");
    private static final BigDecimal OPERATIONAL_FACTOR = new BigDecimal("1.15");
    private static final BigDecimal BEER_FACTOR = new BigDecimal("1.25");
    private static final BigDecimal JUICE_FACTOR = new BigDecimal("1.10");

    private final DrinkRepository drinkRepository;
    private final BbqMenuRepository bbqMenuRepository;

    public BudgetCalculationService(DrinkRepository drinkRepository, BbqMenuRepository bbqMenuRepository) {
        this.drinkRepository = drinkRepository;
        this.bbqMenuRepository = bbqMenuRepository;
    }

    public BigDecimal hourlyRate(int eventDurationHours) {
        if (eventDurationHours <= 0) {
            throw new IllegalArgumentException("A duração do evento deve ser maior que zero.");
        }
        int extraHours = Math.max(0, eventDurationHours - BASE_EVENT_HOURS);
        return BigDecimal.ONE.add(EXTRA_HOUR_RATE.multiply(BigDecimal.valueOf(extraHours)));
    }

    public BigDecimal calculateBBQ(BBQ type, int adults, int childrenUnder12, int eventDurationHours) {
        if (type == null) {
            throw new IllegalArgumentException("Informe o tipo de churrasco.");
        }
        requireNonNegative(adults, "A quantidade de adultos");
        requireNonNegative(childrenUnder12, "A quantidade de crianças");
        if (adults == 0 && childrenUnder12 == 0) {
            throw new IllegalArgumentException("Informe pelo menos um convidado.");
        }

        BigDecimal durationFactor = hourlyRate(eventDurationHours);
        BigDecimal equivalentGuests = BigDecimal.valueOf(adults)
                .add(BigDecimal.valueOf(childrenUnder12).multiply(CHILD_RATE));
        BigDecimal basePrice = bbqMenuRepository.findBasePriceByType(type)
                .orElseThrow(() -> new IllegalStateException("Preço não cadastrado para o cardápio: " + type));

        return basePrice.multiply(equivalentGuests)
                .multiply(durationFactor)
                .multiply(OPERATIONAL_FACTOR)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateBeer(int quantityBrahma600ml, int quantityHeineken600ml, int quantitySkol600ml) {
        requireNonNegative(quantityBrahma600ml, "A quantidade de Brahma");
        requireNonNegative(quantityHeineken600ml, "A quantidade de Heineken");
        requireNonNegative(quantitySkol600ml, "A quantidade de Skol");
        return drinkSubtotal(NameDrink.BRAHMA, quantityBrahma600ml)
                .add(drinkSubtotal(NameDrink.HEINEKEN, quantityHeineken600ml))
                .add(drinkSubtotal(NameDrink.SKOL, quantitySkol600ml))
                .multiply(BEER_FACTOR)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateJuice(int quantityLaranja, int quantityMaracuja, int quantityAbacaxi) {
        requireNonNegative(quantityLaranja, "A quantidade de suco de laranja");
        requireNonNegative(quantityMaracuja, "A quantidade de suco de maracujá");
        requireNonNegative(quantityAbacaxi, "A quantidade de suco de abacaxi");
        return drinkSubtotal(NameDrink.LARANJA, quantityLaranja)
                .add(drinkSubtotal(NameDrink.MARACUJA, quantityMaracuja))
                .add(drinkSubtotal(NameDrink.ABACAXI, quantityAbacaxi))
                .multiply(JUICE_FACTOR)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcTotalBudget(BigDecimal totalBuffet, BigDecimal totalBeer, BigDecimal totalJuice) {
        return nonNegativeTotal(totalBuffet)
                .add(nonNegativeTotal(totalBeer))
                .add(nonNegativeTotal(totalJuice))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal drinkSubtotal(NameDrink drink, int quantity) {
        if (quantity == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal price = drinkRepository.findPriceByNameDrink(drink)
                .orElseThrow(() -> new IllegalStateException("Preço não cadastrado para a bebida: " + drink));
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void requireNonNegative(int quantity, String description) {
        if (quantity < 0) {
            throw new IllegalArgumentException(description + " não pode ser negativa.");
        }
    }

    private BigDecimal nonNegativeTotal(BigDecimal total) {
        if (total == null) {
            return BigDecimal.ZERO;
        }
        if (total.signum() < 0) {
            throw new IllegalArgumentException("Os valores do orçamento não podem ser negativos.");
        }
        return total;
    }
}
