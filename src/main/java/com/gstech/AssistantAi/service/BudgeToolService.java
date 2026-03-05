package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.Buffet;
import com.gstech.AssistantAi.service.utils.BudgetCalculator;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class BudgeToolService {

    private final BudgetCalculator calculateBudge;

    public BudgeToolService(BudgetCalculator calculateBudge) {
        this.calculateBudge = calculateBudge;
    }

    @Tool("Essa função so deverá ser chamada se todos os dados ja tiverem sido passado. Orçamento para um evento, considerando o número de convidados e o tipo de buffet escolhido")
    public BigDecimal calculateBudgetBuffetBBQAndTraditional(
            @P("Tipo de buffet escolhido") Buffet buffetType,
            @P("quantidade de adultos") int adults,
            @P("Crianças menores que 6 anos ?") boolean includeChildrenUnder6,
            @P("Crianças menores que 12 anos ?") boolean includeChildrenUnder12,
            @P("quantidade de crianças menores de 6 anos") int childrenUnder6,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours
            ) {

            double sumGuests;
            double operationalCost = 1.15;
            double totalChildrenUnder6 = 0;
            double totalChildrenUnder12 = 0;

            if (includeChildrenUnder6) {
                totalChildrenUnder6 = childrenUnder6 * 0.25;
            }

            if (includeChildrenUnder12) {
                totalChildrenUnder12 = childrenUnder12 * 0.50;
            }

            sumGuests = adults + totalChildrenUnder6 + totalChildrenUnder12;
            int sumTotalGuests = (int) Math.ceil(sumGuests);


            return calculateBudge.calculateBuffetSelection(buffetType, (int) sumTotalGuests, eventDurationHours)
                    .multiply(BigDecimal.valueOf(operationalCost));
    }


    // orcamento de cervejas
    @Tool("Esse metodo deverá ser chamado se o cliente solicitar cerveja no Orçamento! Orçamento de cervejas com sugestão automática baseada nos convidados ou quantidade informada pelo cliente")
    public BigDecimal calculateBudgetBeer(

            @P("Quantidade de adultos") int adults,

            @P("Deseja cerveja?") boolean includeBeer,
            @P("Deseja brahma") boolean includeBrahma,
            @P("Deseja skol") boolean includeSkol,
            @P("Deseja heineken") boolean includeHeineken,
            @P("Quantidade Brahma?") int quantityBrahma600ml,
            @P("Quantidade Heineken?") int quantityHeineken600ml,
            @P("Quantidade Skol?") int quantitySkol600ml

    ) {

        BigDecimal sumTotal = BigDecimal.ZERO;

        if (includeBeer) {
            sumTotal = sumTotal.add(calculateBudge.calculateBeerSelection(adults, includeBrahma, includeHeineken, includeSkol, quantityBrahma600ml
            , quantityHeineken600ml, quantitySkol600ml));
        }
        return sumTotal;
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

        BigDecimal sumTotal = BigDecimal.ZERO;

        if (includeJuice) {
            sumTotal = sumTotal.add(calculateBudge.calculateJuiceSelection(adults, children, includeLaranja, includeMaracuja, includeAbacaxi, quantityLaranja, quantityMaracuja, quantityAbacaxi));
        }
        return sumTotal;
    }

}

