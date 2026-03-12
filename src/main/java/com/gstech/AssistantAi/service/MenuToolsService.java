package com.gstech.AssistantAi.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

@Service
public class MenuToolsService {

    @Tool("""
            Busca o cardápio oficial de bebidas da Brasa's Churrascaria, incluindo sucos naturais e cervejas disponíveis para os clientes.
            """)
    public String menuDrinks() {

        System.out.println("Ferramenta `menuDrinks` foi chamada para fornecer o cardápio de bebidas.");

        return """
          
                🍹 Bebidas:
                
                Sucos naturais (1L): 
                Laranja
                Abacaxi
                Maracujá
                
                Cervejas (600 ML):
                Skol               --> R$ 9,00
                Brahma             --> R$ 9,00
                Heineken           -- > R$ 12,00
                
                """;
    }

    @Tool("""
            Busca o cardápio oficial da Brasa's Churrascaria, contendo os itens detalhados dos serviços Premium e Essencial.
            """)
    public String bbqMenu() {


        System.out.println("Ferramenta `bbqMenu` foi chamada para fornecer o cardápio do churrasco.");

        return """
            
            ### CARDÁPIO DO CHURRASCO PREMIUM ###
            
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
            
            
            ### CARDÁPIO DO CHURRASCO ESSENCIAL ###
            
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
