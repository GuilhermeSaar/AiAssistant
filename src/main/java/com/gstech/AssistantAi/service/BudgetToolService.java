package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.BBQ;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BudgetToolService {

    private final BudgetCalculationService budgetCalculationService;

    public BudgetToolService(BudgetCalculationService budgetCalculationService) {
        this.budgetCalculationService = budgetCalculationService;
    }

    @Tool("""
            Calcula o valor do churrasco. Use APENAS após ter: Tipo de churrasco, Adultos, Crianças e Duração.
            """)
    public BigDecimal budgetBBQ(
            @P("Tipo de churrasco escolhido") BBQ bbqType,
            @P("quantidade de adultos") int adults,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours) {

        return budgetCalculationService.calculateBBQ(bbqType, adults,
                childrenUnder12, eventDurationHours);
    }

    @Tool("""
            Calcula o valor das cervejas, incluindo a taxa de 25%. Confirme as quantidades antes de chamar.
            Use zero para as marcas não escolhidas e apenas quantidades não negativas.
            """)
    public BigDecimal budgetBeer(
            @P("Quantidade de garrafas de 600ml Brahma") int quantityBrahma600ml,
            @P("Quantidade de garrafas de 600ml Heineken") int quantityHeineken600ml,
            @P("Quantidade de garrafas de 600ml Skol") int quantitySkol600ml) {

        return budgetCalculationService.calculateBeer(quantityBrahma600ml,
                quantityHeineken600ml, quantitySkol600ml);
    }

    @Tool("""
            Calcula o valor dos sucos, incluindo a taxa de 10%. Confirme as quantidades antes de chamar.
            Use zero para sabores não escolhidos e apenas quantidades inteiras não negativas de litros.
            """)
    public BigDecimal budgetJuice(
            @P("Quantidade de litros de suco de laranja") int quantityLaranja,
            @P("Quantidade de litros de suco de maracujá") int quantityMaracuja,
            @P("Quantidade de litros de suco de abacaxi") int quantityAbacaxi) {

        return budgetCalculationService.calculateJuice(quantityLaranja, quantityMaracuja, quantityAbacaxi);
    }

    @Tool("""
            Calcula o valor total do orçamento somando churrasco, cervejas e sucos. Use APENAS após ter os valores individuais de cada item.
            """)
    public BigDecimal sumTotalBudget(
            @P("Valor total do serviço") BigDecimal totalBbq,
            @P("Valor total das cervejas") BigDecimal totalBeer,
            @P("Valor total dos sucos") BigDecimal totalJuice) {

        return budgetCalculationService.calcTotalBudget(totalBbq, totalBeer, totalJuice);
    }
}
