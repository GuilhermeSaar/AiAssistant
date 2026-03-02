package com.gstech.AssistantAi.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgeToolService {


    @Tool("Quando o cliente solicitar um orçamento, utilize esta função para listar os serviços disponíveis")
    public List<String> listServices() {

        return List.of(
                "1 - Casamento ",
                "2 - Festa de aniversário ",
                "3 - Evento corporativo "
        );
    }

    @Tool("Opções oferecidas no buffet de tradicional")
    public String buffetTraditional() {

        return """
                🍛 Pratos Principais:
              
                Estrogonofe de frango
                Filé de frango grelhado
                Carne assada
                Lasanha à bolonhesa
                Feijoada
       
                Acompanhamentos:
               
                Arroz branco
                Arroz temperado
                Feijão
                Purê de batata
                Salada
               
               """;

    }

    @Tool("Opções oferecidas no buffet de churrasco")
    public String buffetBarbecue() {

        return """
                
                Picanha
                Contra-filé
                Fraldinha
                Linguiça toscana
                Coração de frango
                Frango (coxinha da asa, sobre-coxa)
                Costela de porco
                Carne de porco (lombo, pernil)
                
                Acompanhamentos:
           
                Arroz branco
                Farofa
                Vinagrete
                Maionese
                Pão de alho
                Salada verdura
                
                """;
    }

    @Tool ("Opções de bebidas")
    public String drinks() {

        return """
                
                Bebidas:
                
                Refrigerante
                Água
                Suco
                Cerveja
                """;
    }

    @Tool("""
    Calcula o orçamento do evento com base no número de convidados.
    Use esta função somente quando o cliente informar claramente a quantidade de convidados.
    """)
    public double budgetCalculator(
            @P("Numero de convidados") int numberOfGuests
    ) {
        double valorPorPessoa = 75;
        return numberOfGuests * valorPorPessoa;
    }
}
