package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.Buffet;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class BudgeToolService {

    private final BudgetService service;

    public BudgeToolService(BudgetService service) {
        this.service = service;
    }

    // orçamento de buffet
    @Tool("Essa função so deverá ser chamada se todos os dados ja tiverem sido passado. Orçamento para um evento, considerando o número de convidados e o tipo de buffet escolhido")
    public BigDecimal budgetBuffet(
            @P("Tipo de buffet escolhido") Buffet buffetType,
            @P("quantidade de adultos") int adults,
            @P("Crianças menores que 6 anos ?") boolean includeChildrenUnder6,
            @P("Crianças menores que 12 anos ?") boolean includeChildrenUnder12,
            @P("quantidade de crianças menores de 6 anos") int childrenUnder6,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours
            ) {


            return service.calculateBuffet(buffetType, adults, includeChildrenUnder6, includeChildrenUnder12,
                    childrenUnder6, childrenUnder12, eventDurationHours);
    }

    // orcamento de cervejas
    @Tool("Esse metodo deverá ser chamado se o cliente solicitar cerveja no Orçamento! Orçamento de cervejas com sugestão automática baseada nos convidados ou quantidade informada pelo cliente")
    public BigDecimal budgetBeer(

            @P("Quantidade de adultos") int adults,

            @P("Deseja cerveja?") boolean includeBeer,
            @P("Deseja brahma") boolean includeBrahma,
            @P("Deseja skol") boolean includeSkol,
            @P("Deseja heineken") boolean includeHeineken,
            @P("Quantidade Brahma?") int quantityBrahma600ml,
            @P("Quantidade Heineken?") int quantityHeineken600ml,
            @P("Quantidade Skol?") int quantitySkol600ml

    ) {

        return service.calculateBeer(adults, includeBeer, includeBrahma, includeHeineken, includeSkol, quantityBrahma600ml
                , quantityHeineken600ml, quantitySkol600ml);
    }

    // orçamento de sucos
    @Tool("Esse metodo deverá ser chamado se o cliente solicitar sucos no orçamento. Orçamento de sucos com sugestão automática baseada nos convidados ou quantidade informada pelo cliente")
    public BigDecimal calculateBudgetJuice(
            @P("Quantidade de adultos") int adults,
            @P("Quantidade de crianças") int children,
            @P("Deseja suco?") boolean includeJuice,
            @P("Deseja suco de laranja?") boolean includeLaranja,
            @P("Deseja suco de maracujá?") boolean includeMaracuja,
            @P("Deseja suco de abacaxi?") boolean includeAbacaxi,
            @P("Quantidade suco de laranja?") int quantityLaranja,
            @P("Quantidade suco de maracujá?") int quantityMaracuja,
            @P("Quantidade suco de abacaxi?") int quantityAbacaxi
    ) {

        return service.calculateJuice(adults, children, includeJuice, includeLaranja, includeMaracuja,
                includeAbacaxi, quantityLaranja, quantityMaracuja, quantityAbacaxi);
    }

    @Tool ("Use este metodo apenas após calcular buffet, cervejas e sucos.\n" +
            "Ele soma todos os valores e retorna o orçamento final do evento.")
    public String sumTotalBudget(
            @P("Valor total do buffet") BigDecimal totalBuffet,
            @P("Valor total das cervejas") BigDecimal totalBeer,
            @P("Valor total dos sucos") BigDecimal totalJuice
    ) {

        BigDecimal totalCost = service.calcTotalBudget(totalBuffet, totalBeer, totalJuice);

        return String.format(
                "Resumo do Orçamento: Buffet (R$ %.2f), Cervejas (R$ %.2f), Sucos (R$ %.2f). Valor Total: R$ %.2f",
                totalBuffet,totalBeer, totalJuice, totalCost
        );
    }
}
