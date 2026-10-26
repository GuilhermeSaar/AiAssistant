package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.dto.BBQOption;
import com.gstech.AssistantAi.dto.DrinkOption;
import com.gstech.AssistantAi.model.BbqMenuDefinition;
import com.gstech.AssistantAi.model.BbqMenuItem;
import com.gstech.AssistantAi.model.Drink;
import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.BbqMenuRepository;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BudgetCalculationService {

    private final DrinkRepository drinkRepository;
    private final BbqMenuRepository bbqMenuRepository;

    public BudgetCalculationService(DrinkRepository drinkRepository, BbqMenuRepository bbqMenuRepository) {
        this.drinkRepository = drinkRepository;
        this.bbqMenuRepository = bbqMenuRepository;
    }

    public DrinkOption getBeerMenu() {
        return getDrinkMenu("Cerveja", "CERVEJAS 600ML");
    }

    public DrinkOption getJuiceMenu() {
        return getDrinkMenu("Suco", "SUCOS 1L");
    }

    public BBQOption getBbqEssencialMenu() {
        return getBbqMenu(BBQ.CHURRASCO_ESSENCIAL);
    }

    public BBQOption getBbqPremiumMenu() {
        return getBbqMenu(BBQ.CHURRASCO_PREMIUM);
    }

    private DrinkOption getDrinkMenu(String category, String title) {
        Map<NameDrink, BigDecimal> drinks = drinkRepository.findByCategory_CategoryNameIgnoreCase(category)
                .stream()
                .collect(Collectors.toMap(
                        Drink::getNameDrink,
                        Drink::getPrice,
                        (first, duplicate) -> first,
                        () -> new EnumMap<>(NameDrink.class)
                ));
        return new DrinkOption(title, drinks);
    }

    private BBQOption getBbqMenu(BBQ type) {
        BbqMenuDefinition menu = bbqMenuRepository.findByType(type)
                .orElseThrow(() -> new IllegalStateException("Cardápio não cadastrado no banco: " + type));

        return new BBQOption(
                menu.getTitle(),
                itemsForSection(menu.getItems(), "beef"),
                itemsForSection(menu.getItems(), "pork"),
                itemsForSection(menu.getItems(), "chicken"),
                itemsForSection(menu.getItems(), "side")
        );
    }

    private List<String> itemsForSection(List<BbqMenuItem> items, String section) {
        return items.stream()
                .filter(item -> section.equals(item.getSection()))
                .map(BbqMenuItem::getItemName)
                .toList();
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
        BigDecimal sumGuests = BigDecimal.valueOf(adults).add(BigDecimal.valueOf(childrenUnder12).multiply(BigDecimal.valueOf(0.50)));

        int roundedGuests = (int) Math.ceil(sumGuests.doubleValue());
        BigDecimal basePrice = bbqMenuRepository.findByType(type)
                .orElseThrow(() -> new IllegalStateException("Preço não cadastrado no banco para o cardápio: " + type))
                .getBasePrice();
        BigDecimal totalCostBBQ = basePrice.multiply(BigDecimal.valueOf(roundedGuests))
                .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));

        return totalCostBBQ.multiply(operationalCost).setScale(2, RoundingMode.HALF_UP);
    }

    // calculo de cerveja
    public BigDecimal calculateBeer(int quantityBrahma600ml, int quantityHeineken600ml, int quantitySkol600ml) {

        BigDecimal taxBeer = BigDecimal.valueOf(1.25);

        BigDecimal totalCost = BigDecimal.valueOf(quantityBrahma600ml)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.BRAHMA))
                .add(BigDecimal.valueOf(quantityHeineken600ml)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.HEINEKEN)))
                .add(BigDecimal.valueOf(quantitySkol600ml)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.SKOL)));

        return totalCost.multiply(taxBeer).setScale(2, RoundingMode.HALF_UP);
    }

    // calculo de suco
    public BigDecimal calculateJuice(int quantityLaranja, int quantityMaracuja, int quantityAbacaxi) {

        BigDecimal taxJuice = BigDecimal.valueOf(1.10);

        BigDecimal totalCost = BigDecimal.valueOf(quantityLaranja)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.LARANJA))
                .add(BigDecimal.valueOf(quantityMaracuja)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.MARACUJA)))
                .add(BigDecimal.valueOf(quantityAbacaxi)
                .multiply(drinkRepository.findPriceByNameDrink(NameDrink.ABACAXI)));

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
