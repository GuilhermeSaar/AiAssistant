package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.Budget;
import com.gstech.AssistantAi.model.enums.Buffet;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class BudgeToolService {


    @Tool("Essa função so deverá ser chamada se todos os dados ja tiverem sido passado. Orçamento para um evento, considerando o número de convidados e o tipo de buffet escolhido")
    public double calculateBudget(Buffet buffet, int adults, int childrenUnder6, int childrenUnder12) {

        var budget = new Budget(adults, childrenUnder12, childrenUnder6);
        double totalPrice = 0.0;

        if (buffet == Buffet.CHURRASCOTIPO1) {

            double pricePerGuest = 49.90;
            double meatKg = budget.calculateMeatKg();
            totalPrice = meatKg * pricePerGuest;
        }
        return totalPrice;
    }
}
