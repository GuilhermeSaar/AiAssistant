package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.BBQ;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.BbqMenuRepository;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetCalculationServiceTest {

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private BbqMenuRepository bbqMenuRepository;

    @InjectMocks
    private BudgetCalculationService service;

    @ParameterizedTest
    @CsvSource({"1,1,4,103.33", "1,3,4,172.21", "2,0,4,137.77", "1,0,5,75.77", "1,0,11,117.10"})
    void calculatesChildrenAndExtraHoursWithoutRoundingGuests(int adults, int children, int hours, String expected) {
        when(bbqMenuRepository.findBasePriceByType(BBQ.CHURRASCO_ESSENCIAL))
                .thenReturn(Optional.of(new BigDecimal("59.90")));
        assertThat(service.calculateBBQ(BBQ.CHURRASCO_ESSENCIAL, adults, children, hours))
                .isEqualByComparingTo(expected);
    }

    @Test
    void usesPremiumPriceFromRepository() {
        when(bbqMenuRepository.findBasePriceByType(BBQ.CHURRASCO_PREMIUM))
                .thenReturn(Optional.of(new BigDecimal("79.90")));
        assertThat(service.calculateBBQ(BBQ.CHURRASCO_PREMIUM, 1, 0, 4)).isEqualByComparingTo("91.89");
    }

    @ParameterizedTest
    @CsvSource({"-1,0,4", "0,-1,4", "0,0,4", "1,0,0", "1,0,-1"})
    void rejectsInvalidEventBeforeQueryingPrices(int adults, int children, int hours) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.calculateBBQ(BBQ.CHURRASCO_ESSENCIAL, adults, children, hours));
        verifyNoInteractions(bbqMenuRepository, drinkRepository);
    }

    @Test
    void requiresMenuType() {
        assertThatIllegalArgumentException().isThrownBy(() -> service.calculateBBQ(null, 1, 0, 4));
        verifyNoInteractions(bbqMenuRepository);
    }

    @Test
    void includesBeerFeeExactlyOnce() {
        price(NameDrink.BRAHMA, "9.00");
        price(NameDrink.HEINEKEN, "12.00");
        price(NameDrink.SKOL, "9.00");
        assertThat(service.calculateBeer(2, 1, 1)).isEqualByComparingTo("48.75");
    }

    @Test
    void includesJuiceFeeExactlyOnce() {
        price(NameDrink.LARANJA, "8.00");
        price(NameDrink.MARACUJA, "8.00");
        price(NameDrink.ABACAXI, "8.00");
        assertThat(service.calculateJuice(2, 1, 1)).isEqualByComparingTo("35.20");
    }

    @Test
    void doesNotRequireUnselectedDrinksToHavePrices() {
        price(NameDrink.BRAHMA, "9.00");
        assertThat(service.calculateBeer(1, 0, 0)).isEqualByComparingTo("11.25");
        verify(drinkRepository).findPriceByNameDrink(NameDrink.BRAHMA);
        verifyNoMoreInteractions(drinkRepository);
    }

    @Test
    void returnsZeroForDrinksNotRequested() {
        assertThat(service.calculateBeer(0, 0, 0)).isEqualByComparingTo("0.00");
        assertThat(service.calculateJuice(0, 0, 0)).isEqualByComparingTo("0.00");
        verifyNoInteractions(drinkRepository);
    }

    @ParameterizedTest
    @CsvSource({"-1,0,0", "0,-1,0", "0,0,-1"})
    void rejectsNegativeDrinkQuantities(int first, int second, int third) {
        assertThatIllegalArgumentException().isThrownBy(() -> service.calculateBeer(first, second, third));
        assertThatIllegalArgumentException().isThrownBy(() -> service.calculateJuice(first, second, third));
        verifyNoInteractions(drinkRepository);
    }

    @Test
    void reportsMissingDrinkPriceInsteadOfThrowingNullPointerException() {
        when(drinkRepository.findPriceByNameDrink(NameDrink.BRAHMA)).thenReturn(Optional.empty());
        assertThatIllegalStateException().isThrownBy(() -> service.calculateBeer(1, 0, 0))
                .withMessageContaining("BRAHMA");
    }

    @Test
    void reportsMissingMenuPrice() {
        when(bbqMenuRepository.findBasePriceByType(BBQ.CHURRASCO_ESSENCIAL)).thenReturn(Optional.empty());
        assertThatIllegalStateException()
                .isThrownBy(() -> service.calculateBBQ(BBQ.CHURRASCO_ESSENCIAL, 1, 0, 4))
                .withMessageContaining("CHURRASCO_ESSENCIAL");
    }

    @Test
    void sumsQuotedValuesWithoutReapplyingFees() {
        assertThat(service.calcTotalBudget(new BigDecimal("103.33"), new BigDecimal("11.25"), null))
                .isEqualByComparingTo("114.58");
        assertThat(service.calcTotalBudget(null, null, null)).isEqualByComparingTo("0.00");
    }

    @ParameterizedTest
    @CsvSource({"-1,0,0", "0,-1,0", "0,0,-1"})
    void rejectsNegativeTotals(BigDecimal buffet, BigDecimal beer, BigDecimal juice) {
        assertThatIllegalArgumentException().isThrownBy(() -> service.calcTotalBudget(buffet, beer, juice));
    }

    private void price(NameDrink drink, String value) {
        when(drinkRepository.findPriceByNameDrink(drink)).thenReturn(Optional.of(new BigDecimal(value)));
    }
}
