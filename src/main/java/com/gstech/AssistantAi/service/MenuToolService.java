package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.model.Meat;
import com.gstech.AssistantAi.model.enums.Buffet;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuToolService {


    @Tool("Opções oferecidas no buffet de tradicional")
    public String traditionalBuffetMenu() {

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
    public String bbqBuffetMenu() {

        return """
                Bovinos:
                Picanha
                Contra-filé
                Fraldinha
                
                Suínos:
                Costela de porco
                Carne de porco (lombo, pernil)
                Linguiça toscana
                
                Aves:
                Coração de frango
                Frango (coxinha da asa, sobre-coxa)
                
                Acompanhamentos:
           
                Arroz branco
                Farofa
                Vinagrete
                Maionese
                Pão de alho
                Salada verdura
                
                """;
    }
}
