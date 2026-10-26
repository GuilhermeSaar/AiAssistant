package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.dto.BBQOption;
import com.gstech.AssistantAi.dto.DrinkOption;
import com.gstech.AssistantAi.model.BbqMenuDefinition;
import com.gstech.AssistantAi.model.BbqMenuItem;
import com.gstech.AssistantAi.model.Drink;
import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.BbqMenuSection;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.BbqMenuRepository;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final DrinkRepository drinkRepository;
    private final BbqMenuRepository bbqMenuRepository;

    public MenuService(DrinkRepository drinkRepository, BbqMenuRepository bbqMenuRepository) {
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
        Map<NameDrink, BigDecimal> drinks = new EnumMap<>(NameDrink.class);
        for (Drink drink : drinkRepository.findByCategory_CategoryNameIgnoreCase(category)) {
            drinks.put(drink.getNameDrink(), drink.getPrice());
        }
        return new DrinkOption(title, drinks);
    }

    private BBQOption getBbqMenu(BBQ type) {
        BbqMenuDefinition menu = bbqMenuRepository.findByType(type)
                .orElseThrow(() -> new IllegalStateException("Cardápio não cadastrado: " + type));
        return new BBQOption(
                menu.getTitle(),
                itemsForSection(menu.getItems(), BbqMenuSection.BEEF),
                itemsForSection(menu.getItems(), BbqMenuSection.PORK),
                itemsForSection(menu.getItems(), BbqMenuSection.CHICKEN),
                itemsForSection(menu.getItems(), BbqMenuSection.SIDE)
        );
    }

    private List<String> itemsForSection(List<BbqMenuItem> items, BbqMenuSection section) {
        return items.stream()
                .filter(item -> item.getSection() == section)
                .map(BbqMenuItem::getItemName)
                .toList();
    }
}
