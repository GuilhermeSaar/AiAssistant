package com.gstech.AssistantAi.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class MenuToolsService {


    // cardapio bebidas
    @Tool("""
            Busca o cardápio oficial de bebidas da Brasa's Churrascaria, incluindo sucos naturais e cervejas disponíveis para os clientes.
            """)
    public String menuDrinks() {

        System.out.println("Ferramenta `menuDrinks` foi chamada para fornecer o cardápio de bebidas.");

        return """
                🍹 **Cardápio de Bebidas**
                
                - **Suco de Laranja** (1 Litro): R$ 8,00
                - **Suco de Abacaxi** (1 Litro): R$ 8,00
                - **Suco de Maracujá** (1 Litro): R$ 8,00
                - **Cerveja Skol** (600 ML): R$ 9,00
                - **Cerveja Brahma** (600 ML): R$ 9,00
                - **Cerveja Heineken** (600 ML): R$ 12,00
                """;
    }

    // cardapio churrasco
    @Tool("""
            Busca o cardápio oficial da Brasa's Churrascaria, contendo os itens detalhados dos serviços Premium e Essencial.
            """)
    public String bbqMenu() {


        System.out.println("Ferramenta `bbqMenu` foi chamada para fornecer o cardápio do churrasco.");

        return """
            ### 🥩 CHURRASCO PREMIUM
            
            - **Bovinos Nobres**: Picanha Angus, Ancho, Chorizo
            - **Suínos**: Costela suína
            - **Aves**: Medalhão de frango com bacon
            - **Entradas**: Queijo coalho, Pão de alho especial
            - **Acompanhamentos**: Arroz branco, Arroz biro-biro, Farofa especial, Salada verde
            
            ---
            
            ### 🍖 CHURRASCO ESSENCIAL
            
            - **Bovinos**: Picanha, Contra-filé
            - **Suínos**: Linguiça toscana
            - **Aves**: Coração de frango, Coxa e sobrecoxa
            - **Acompanhamentos**: Arroz branco, Farofa, Vinagrete, Maionese, Pão de alho
            """;
    }
}
