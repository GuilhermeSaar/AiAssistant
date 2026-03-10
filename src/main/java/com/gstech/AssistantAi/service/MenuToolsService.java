package com.gstech.AssistantAi.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class MenuToolsService {

    @Tool("""
            Opção de bebidas oferecidas.
            
            Use quando o cliente perguntar:
            - quais opçoes de bebidas
            - qual o cardápio das bebidas
            - quais bebidas vocês oferecem
            """)
    public String menuDrinks() {

        return """
          
                🍹 Bebidas:
                
                Sucos naturais (1L):
                Laranja
                Abacaxi
                Maracujá
                
                Cervejas (600 ML):
                Skol
                Brahma
                Heineken
                
                """;

    }

    @Tool("""
            Mostra o cardápio do churrasco premium.

            Use quando o cliente perguntar:
            - o que tem no churrasco premium
            - qual o cardápio premium
            - quais carnes tem no premium
            
            """)
    public String bbqMenuPremium() {

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

    @Tool("""
            Mostra o cardápio do churrasco essencial.

            Use quando o cliente perguntar:
            - o que tem no churrasco essencial
            - qual o cardápio essencial
            - quais carnes tem no essencial
            """)
    public String bbqMenuEssencial() {

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
