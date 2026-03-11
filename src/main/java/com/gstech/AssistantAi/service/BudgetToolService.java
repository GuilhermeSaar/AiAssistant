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
                Calcula o custo do churrasco para o evento.

                Use esta ferramenta quando o cliente informar:
                - tipo de churrasco escolhido (Premium, Essencial ou Tradicional)
                - quantidade de adultos
                - quantidade de crianças
                - duração do evento

                Retorna o valor total do churrasco.
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
            Calcula o custo de cervejas para o evento.

            Use esta ferramenta quando o cliente solicitar cerveja no orçamento.

            Importante:
            - todas as cervejas são consideradas garrafas de 600ml.

            Regras:
            - Utilize as quantidades informadas pelo cliente se existirem.
            - Caso o cliente não informe quantidades, o sistema deve sugerir uma quantidade automática baseada no número de adultos.
            - Retorna apenas o valor total das cervejas.
            """)
    public BigDecimal budgetBeer(

            @P("Quantidade de adultos") int adults,
            @P("Quantidade de garrafas de 600ml Brahma") int quantityBrahma600ml,
            @P("Quantidade de garrafas de 600ml Heineken") int quantityHeineken600ml,
            @P("Quantidade de garrafas de 600ml Skol") int quantitySkol600ml

    ) {

        System.out.printf("CALCULANDO CERVEJA... %.2f", service.calculateBeer(adults, true, quantityBrahma600ml, quantityHeineken600ml, quantitySkol600ml));

        return service.calculateBeer(adults, true, quantityBrahma600ml,
                quantityHeineken600ml, quantitySkol600ml);
    }

    // orçamento de sucos
    @Tool("""
                Calcula o custo dos sucos do evento.

                Use quando o cliente solicitar sucos no orçamento.

                O cliente pode:
                - informar quantidades específicas
                - ou solicitar a quantidade sugerida baseada nos convidados

                Retorna o valor total dos sucos.
            """)
    public BigDecimal budgetJuice(
            @P("Quantidade de adultos") int adults,
            @P("Quantidade de crianças") int children,
            @P("Quantidade suco de laranja?") int quantityLaranja,
            @P("Quantidade suco de maracujá?") int quantityMaracuja,
            @P("Quantidade suco de abacaxi?") int quantityAbacaxi) {

        System.out.printf("CALCULANDO SUCOS... %.2f", service.calculateJuice(adults, children, true,
                quantityLaranja, quantityMaracuja, quantityAbacaxi));

        return service.calculateJuice(adults, children, true, quantityLaranja, quantityMaracuja, quantityAbacaxi);
    }

    @Tool("""
                Soma todos os valores do orçamento do evento.

                Use apenas depois que o tipo de churrasco, cervejas e sucos forem calculados.

                Retorna o valor total do evento.
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
