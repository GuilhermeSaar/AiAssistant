package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.Budget;
import com.gstech.AssistantAi.model.enums.Buffet;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

// regreas de negócios

// 1 - regra de minimo de convidados e maximo
// 2 - regra por tipo de buffet escolhido, cada um tem um valor diferente
// 3 - regras por duração de eventos
// 4 - regras de forma de pagamento: a vista/ parcelado

@Component
public class BudgeToolService {

    @Tool("Essa função so deverá ser chamada se todos os dados ja tiverem sido passado. Orçamento para um evento, considerando o número de convidados e o tipo de buffet escolhido")
    public BigDecimal calculateBudget(
            @P("Tipo de buffet escolhido") Buffet buffetType,
            @P("quantidade de adultos") int adults,
            @P("quantidade de crianças menores de 6 anos") int childrenUnder6,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours
            ) {

            double hourlyRate = 1.0;
            double operationalCost = 1.15;
            if (eventDurationHours > 4) {
                hourlyRate = 1.2;
            }
            var budget = new Budget(adults, childrenUnder6, childrenUnder12);
            //double kgMeat = budget.calculateMeatKg();
            double totalGuests = adults + (childrenUnder6 * 0.25) + (childrenUnder12 * 0.50);

            double costPerGuest;
            BigDecimal totalCost = BigDecimal.ZERO;

            if (buffetType == Buffet.CHURRASCO_ESSENCIAL) {

                costPerGuest = 49.90;
                totalCost = BigDecimal.valueOf(costPerGuest).multiply(BigDecimal.valueOf(totalGuests))
                        .multiply(BigDecimal.valueOf(hourlyRate));
            }

            else if (buffetType == Buffet.CHURRASCO_PREMIUM) {

                costPerGuest = 69.90;
                totalCost = BigDecimal.valueOf(costPerGuest).multiply(BigDecimal.valueOf(totalGuests))
                        .multiply(BigDecimal.valueOf(hourlyRate));
            }

            return totalCost
                .multiply(BigDecimal.valueOf(operationalCost))
                .setScale(2, RoundingMode.HALF_UP);
    }
}

