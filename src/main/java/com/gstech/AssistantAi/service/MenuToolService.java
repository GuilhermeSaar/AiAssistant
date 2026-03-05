package com.gstech.AssistantAi.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class MenuToolService {

    @Tool("Cardápio de bebidas")
    public String menuDrinks() {

        return """
          
                🍹 Bebidas:
                
                Refrigerantes (2L):
                Coca-Cola
                Guaraná
                Fanta
                
                Sucos naturais (1L):
                Laranja
                Abacaxi
                Maracujá
                
                Água mineral (500 ML):
                com e sem gás
                
                Cervejas (600 ML):
                Skol
                Brahma
                Heineken
                
                """;

    }

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

    @Tool("Opção Premium oferecida no buffet de churrasco")
    public String bbqBuffetPremium() {

        return """
            Bovinos Nobres:
            Picanha Angus
            Ancho
            Chorizo
            
            Suínos:
            Costela suína
            
            Aves:
            Medalhão de frango com bacon
            
            Entradas:
            Queijo coalho
            Pão de alho especial
            
            Acompanhamentos:
            Arroz branco
            Arroz biro-biro
            Farofa especial
            Salada verde
            """;
    }

    @Tool("Opção Essencial oferecida no buffet de churrasco")
    public String bbqBuffetEssencial() {

        return """
            Bovinos:
            Picanha
            Contra-filé
            
            Suínos:
            Linguiça toscana
            
            Aves:
            Coração de frango
            Coxa e sobrecoxa
            
            Acompanhamentos:
            Arroz branco
            Farofa
            Vinagrete
            Maionese
            Pão de alho
            """;
    }
}
