package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.dto.BBQOption;
import com.gstech.AssistantAi.dto.DrinkOption;
import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class BudgetCalculationService {

    private final DrinkRepository repository;

    public BudgetCalculationService(DrinkRepository repository) {
        this.repository = repository;
    }

    public DrinkOption getBeerMenu() {

        return new DrinkOption(
                "CERVEJAS 600ML",
                Map.of(
                        NameDrink.SKOL, repository.findPriceByNameDrink(NameDrink.SKOL),
                        NameDrink.BRAHMA, repository.findPriceByNameDrink(NameDrink.BRAHMA),
                        NameDrink.HEINEKEN, repository.findPriceByNameDrink(NameDrink.HEINEKEN)
                )
        );
    }

    public DrinkOption getJuiceMenu() {

        return new DrinkOption(
                "SUCOS 1L",
                Map.of(
                        NameDrink.LARANJA, repository.findPriceByNameDrink(NameDrink.LARANJA),
                        NameDrink.MARACUJA, repository.findPriceByNameDrink(NameDrink.MARACUJA),
                        NameDrink.ABACAXI, repository.findPriceByNameDrink(NameDrink.ABACAXI)
                )
        );
    }

    public BBQOption getBbqEssencialMenu() {

        return new BBQOption(
                "CHURRASCO ESSENCIAL",
                List.of("Picanha", "Contra-filé"),
                List.of("Linguiça toscana"),
                List.of("Coração de frango", "Coxa e sobrecoxa"),
                List.of("Arroz branco", "Farofa", "Vinagrete", "Maionese", "Pão de alho")
        );
    }

    public BBQOption getBbqPremiumMenu() {

        return new BBQOption(
                "CHURRASCO PREMIUM",
                List.of("Picanha Angus", "Ancho", "Chorizo"),
                List.of("Costela suína", "Linguiça defumada"),
                List.of("Medalhão de frango com bacon", "Asinhas de frango"),
                List.of("Queijo coaclho", "Pão de alho especial", "Arroz branco", "Farofa especial", "Salada verde")
        );
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

        BigDecimal operationalCost = BigDecimal.valueOf(1.15);
        BigDecimal totalCostBBQ = BigDecimal.ZERO;
        BigDecimal sumGuests = BigDecimal.valueOf(adults).add(BigDecimal.valueOf(childrenUnder12).multiply(BigDecimal.valueOf(0.50)));

        int roundedGuests = (int) Math.ceil(sumGuests.doubleValue());

        if (BBQ.CHURRASCO_PREMIUM == type) {
            totalCostBBQ = BigDecimal.valueOf(79.90).multiply(BigDecimal.valueOf(roundedGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        else if (BBQ.CHURRASCO_ESSENCIAL == type) {
            totalCostBBQ = BigDecimal.valueOf(59.90).multiply(BigDecimal.valueOf(roundedGuests))
                    .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
        }

        return totalCostBBQ.multiply(operationalCost).setScale(2, RoundingMode.HALF_UP);
    }

    // calculo de cerveja
    public BigDecimal calculateBeer(int quantityBrahma600ml, int quantityHeineken600ml, int quantitySkol600ml) {

        BigDecimal taxBeer = BigDecimal.valueOf(1.25);

        BigDecimal totalCost = BigDecimal.valueOf(quantityBrahma600ml)
                .multiply(repository.findPriceByNameDrink(NameDrink.BRAHMA))
                .add(BigDecimal.valueOf(quantityHeineken600ml)
                .multiply(repository.findPriceByNameDrink(NameDrink.HEINEKEN)))
                .add(BigDecimal.valueOf(quantitySkol600ml)
                .multiply(repository.findPriceByNameDrink(NameDrink.SKOL)));

        return totalCost.multiply(taxBeer).setScale(2, RoundingMode.HALF_UP);
    }

    // calculo de suco
    public BigDecimal calculateJuice(int quantityLaranja, int quantityMaracuja, int quantityAbacaxi) {

        BigDecimal taxJuice = BigDecimal.valueOf(1.10);

        BigDecimal totalCost = BigDecimal.valueOf(quantityLaranja)
                .multiply(repository.findPriceByNameDrink(NameDrink.LARANJA))
                .add(BigDecimal.valueOf(quantityMaracuja)
                .multiply(repository.findPriceByNameDrink(NameDrink.MARACUJA)))
                .add(BigDecimal.valueOf(quantityAbacaxi)
                .multiply(repository.findPriceByNameDrink(NameDrink.ABACAXI)));

        return totalCost.multiply(taxJuice).setScale(2, RoundingMode.HALF_UP);
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
