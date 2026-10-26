package com.gstech.AssistantAi;

import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.BbqMenuRepository;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import com.gstech.AssistantAi.service.BudgetCalculationService;
import com.gstech.AssistantAi.service.MenuService;
import dev.langchain4j.model.chat.ChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
@ActiveProfiles("test")
class AssistantAiApplicationTests {

    @MockitoBean
    private ChatModel chatModel;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BudgetCalculationService budgetCalculationService;

    @Autowired
    private BbqMenuRepository bbqMenuRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    void loadsSeedDataWithoutCallingProvider() {
        assertThat(drinkRepository.count()).isEqualTo(6);
        assertThat(bbqMenuRepository.count()).isEqualTo(2);
        verifyNoInteractions(chatModel);
    }

    @Test
    void findsMenuByItsEnumIdentifier() {
        assertThat(bbqMenuRepository.findById(BBQ.CHURRASCO_PREMIUM))
                .hasValueSatisfying(menu -> assertThat(menu.getBasePrice()).isEqualByComparingTo("79.90"));
    }

    @Test
    void readsDrinkMenusFromH2ByCategory() {
        assertThat(menuService.getJuiceMenu().drink())
                .containsOnlyKeys(NameDrink.LARANJA, NameDrink.ABACAXI, NameDrink.MARACUJA)
                .containsEntry(NameDrink.LARANJA, new BigDecimal("8.00"));
        assertThat(menuService.getBeerMenu().drink())
                .containsOnlyKeys(NameDrink.BRAHMA, NameDrink.HEINEKEN, NameDrink.SKOL)
                .containsEntry(NameDrink.HEINEKEN, new BigDecimal("12.00"));
    }

    @Test
    void loadsMenuItemsInTheirDisplayOrder() {
        var menu = menuService.getBbqPremiumMenu();
        assertThat(menu.beefs()).containsExactly("Picanha Angus", "Ancho", "Chorizo");
        assertThat(menu.porks()).containsExactly("Costela suína", "Linguiça defumada");
        assertThat(menu.chickens()).containsExactly("Medalhão de frango com bacon", "Asinhas de frango");
    }

    @Test
    void calculatesQuoteWithPriceStoredInH2() {
        assertThat(budgetCalculationService.calculateBBQ(BBQ.CHURRASCO_ESSENCIAL, 1, 1, 4))
                .isEqualByComparingTo("103.33");
    }

}
