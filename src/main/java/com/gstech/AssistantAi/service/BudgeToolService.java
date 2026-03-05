package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.enums.Buffet;
import com.gstech.AssistantAi.model.enums.NameDrink;
import com.gstech.AssistantAi.repositories.DrinkRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BudgeToolService {

    @Autowired
    private DrinkRepository repository;

    @Tool("Essa função so deverá ser chamada se todos os dados ja tiverem sido passado. Orçamento para um evento, considerando o número de convidados e o tipo de buffet escolhido")
    public BigDecimal calculateBudgetBuffetBBQ(
            @P("Tipo de buffet escolhido") Buffet buffetType,
            @P("quantidade de adultos") int adults,
            @P("quantidade de crianças menores de 6 anos") int childrenUnder6,
            @P("quantidade de crianças menores de 12 anos") int childrenUnder12,
            @P("Duração do evento em horas") int eventDurationHours
            ) {

            double operationalCost = 1.15;
            double totalGuests = adults + (childrenUnder6 * 0.25) + (childrenUnder12 * 0.50);

            double costPerGuest;
            BigDecimal totalCost = BigDecimal.ZERO;

            if (buffetType == Buffet.CHURRASCO_ESSENCIAL) {

                costPerGuest = 59.90;
                totalCost = BigDecimal.valueOf(costPerGuest).multiply(BigDecimal.valueOf(totalGuests))
                        .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
            }

            else if (buffetType == Buffet.CHURRASCO_PREMIUM) {

                costPerGuest = 79.90;
                totalCost = BigDecimal.valueOf(costPerGuest).multiply(BigDecimal.valueOf(totalGuests))
                        .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));
            }

            else if (buffetType == Buffet.TRADICIONAL) {

                costPerGuest = 49.90;
                totalCost = BigDecimal.valueOf(costPerGuest).multiply(BigDecimal.valueOf(totalGuests))
                        .multiply(BigDecimal.valueOf(hourlyRate(eventDurationHours)));

            }

            return totalCost
                .multiply(BigDecimal.valueOf(operationalCost))
                .setScale(2, RoundingMode.HALF_UP);
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
            sumTotal = sumTotal.add(calculateBeerSelection(adults, includeBrahma, includeHeineken, includeSkol, quantityBrahma600ml
            , quantityHeineken600ml, quantitySkol600ml));
        }
        return sumTotal;
    }

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
            sumTotal = sumTotal.add(calculateJuiceSelection(adults, children, includeLaranja, includeMaracuja, includeAbacaxi, quantityLaranja, quantityMaracuja, quantityAbacaxi));
        }
        return sumTotal;
    }


    // calculo suco
    public BigDecimal calculateJuice(int adults, int children, int qtdJuice, NameDrink nameJuice) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pricePerLiter = repository.findPriceByNameDrink(nameJuice);

        if (qtdJuice > 0) {
            total = total.add(
                    BigDecimal.valueOf(qtdJuice)
                            .multiply(pricePerLiter)
            );
        }  else {

            double qtdLiters = (adults * 0.6) + (children * 0.8);
            int unitsLiters = (int) Math.ceil(qtdLiters);
            total = total.add(
                    BigDecimal.valueOf(unitsLiters)
                            .multiply(pricePerLiter));
        }
        return total;
    }


    // selecao de sucos e refrigerantes escolhidas pelo cliente
    private BigDecimal calculateJuiceSelection(int adults, int children, boolean laranja, boolean maracuja, boolean abacaxi,
                                               int qtdLaranja,
                                               int qtdMaracuja, int qtdAbacaxi) {
        BigDecimal sumTotal = BigDecimal.ZERO;

        if (laranja) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdLaranja, NameDrink.LARANJA));
        }

        if (maracuja) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdMaracuja, NameDrink.MARACUJA));
        }

        if (abacaxi) {
            sumTotal = sumTotal.add(calculateJuice(adults, children, qtdAbacaxi, NameDrink.ABACAXI));
        }
        return sumTotal;
    }


    // selecao de cervejas escolhidas pelo cliente
    private BigDecimal calculateBeerSelection(int adults, boolean brahma, boolean heineken, boolean skol, int qtdBrahma600ml,
                                    int qtdHeineken600ml, int qtdSkol600ml) {
        BigDecimal sumTotal = BigDecimal.ZERO;

        if (brahma) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdBrahma600ml, NameDrink.BRAHMA));
        }

        if (heineken) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdHeineken600ml, NameDrink.HEINEKEN));
        }

        if (skol) {
            sumTotal = sumTotal.add(calculateBeer(adults, qtdSkol600ml, NameDrink.SKOL));
        }
        return sumTotal;
    }


    // calculo cerveja
    private BigDecimal calculateBeer(int adults, int qtdBeer600ml, NameDrink nameDrink) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal pricePerUnit = repository.findPriceByNameDrink(nameDrink);

        if (qtdBeer600ml > 0) {
            total = total.add(
                    BigDecimal.valueOf(qtdBeer600ml)
                            .multiply(pricePerUnit)
            );

        } else {
            double liters = adults * 1.50;
            int units600ml = (int) Math.ceil(liters / 0.6);
            total = total.add(
                    BigDecimal.valueOf(units600ml)
                            .multiply(pricePerUnit));
        }
        return total;
    }

    // taxa de hora extra para eventos com duracao maior que 4 horas
    private double hourlyRate(int eventDurationHours) {
        int extraHours = eventDurationHours - 4;

        if (extraHours <= 0) {
            return 1.0;
        }
        return 1.0 + (extraHours * 0.10);
    }
}

