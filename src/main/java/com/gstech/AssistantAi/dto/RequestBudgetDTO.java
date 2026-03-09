package com.gstech.AssistantAi.dto;

import java.math.BigDecimal;

public record RequestBudgetDTO(

        BigDecimal beerTotal,
        BigDecimal juiceTotal,
        BigDecimal buffetTotal,
        BigDecimal calculateTotalBudget
) {
}
