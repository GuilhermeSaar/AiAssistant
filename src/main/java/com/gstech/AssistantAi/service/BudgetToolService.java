package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.BBQ;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BudgetToolService {

    private final BudgetCalculationService service;

    public BudgetToolService(BudgetCalculationService service) {
        this.service = service;

    }

    // orçamento do tipo de churrasco
    @Tool("""
            Calcula o valor do churrasco. Use APENAS após ter: Tipo de churrasco, Adultos, Crianças e Duração.
            """)
    public BigDecimal budgetBBQ(
            @P("Tipo de churrasco escolhido") BBQ bbqType,
            @P("quantidade de adultos") int adults,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours) {

        System.out.printf("CALCULANDO CHURRASCO... %.2f",
                service.calculateBBQ(bbqType, adults,
                        childrenUnder12, eventDurationHours));

        return service.calculateBBQ(bbqType, adults,
                childrenUnder12, eventDurationHours);
    }

    // orcamento de cervejas
    @Tool("""
            Calcula o valor das cervejas. Use APENAS após definir o churrasco e confirmar se o cliente quer cerveja.
            """)
    public BigDecimal budgetBeer(
            @P("Quantidade de garrafas de 600ml Brahma") int quantityBrahma600ml,
            @P("Quantidade de garrafas de 600ml Heineken") int quantityHeineken600ml,
            @P("Quantidade de garrafas de 600ml Skol") int quantitySkol600ml

    ) {

        System.out.printf("CALCULANDO CERVEJA... %.2f", service.calculateBeer(quantityBrahma600ml, quantityHeineken600ml, quantitySkol600ml));

        return service.calculateBeer(quantityBrahma600ml,
                quantityHeineken600ml, quantitySkol600ml);
    }

    // orçamento de sucos
    @Tool("""
            Calcula o valor dos sucos. Use APENAS após definir o churrasco e confirmar se o cliente quer suco.
            """)
    public BigDecimal budgetJuice(
            @P("Quantidade suco de laranja?") int quantityLaranja,
            @P("Quantidade suco de maracujá?") int quantityMaracuja,
            @P("Quantidade suco de abacaxi?") int quantityAbacaxi) {

        System.out.printf("CALCULANDO SUCOS... %.2f", service.calculateJuice(
                quantityLaranja, quantityMaracuja, quantityAbacaxi));

        return service.calculateJuice(quantityLaranja, quantityMaracuja, quantityAbacaxi);
    }

    // calculo total do orçamento.
    @Tool("""
            Calcula o valor total do orçamento somando churrasco, cervejas e sucos. Use APENAS após ter os valores individuais de cada item.
            """)
    public BigDecimal sumTotalBudget(
            @P("Valor total do serviço") BigDecimal totalBbq,
            @P("Valor total das cervejas") BigDecimal totalBeer,
            @P("Valor total dos sucos") BigDecimal totalJuice) {

        System.out.printf("CALCULANDO TOTAL ORÇAMENTO... %.2f",
                service.calcTotalBudget(totalBbq, totalBeer, totalJuice));

        return service.calcTotalBudget(totalBbq, totalBeer, totalJuice);
    }
}
