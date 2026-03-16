package com.gstech.AssistantAi.service;

import com.gstech.AssistantAi.dto.BBQMenu;
import com.gstech.AssistantAi.dto.BBQOption;
import com.gstech.AssistantAi.dto.DrinkMenu;
import com.gstech.AssistantAi.dto.DrinkOption;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuToolsService {

    private final BudgetCalculationService service;

    public MenuToolsService(BudgetCalculationService service) {
        this.service = service;
    }

    // cardapio bebidas
    @Tool("""
            Busca o cardápio oficial de bebidas da Brasa's Churrascaria, incluindo sucos naturais e cervejas disponíveis para os clientes.
            """)
    public DrinkMenu menuDrinks() {

        return new DrinkMenu(service.getBeerMenu(), service.getJuiceMenu());
    }

    // cardapio churrasco
    @Tool("""
            Busca o cardápio oficial da Brasa's Churrascaria, contendo os itens detalhados dos serviços Premium e Essencial.
            """)
    public BBQMenu bbqMenu() {

        BBQOption premium = new BBQOption(
                "CHURRASCO PREMIUM",
                List.of("Picanha Angus", "Ancho", "Chorizo"),
                List.of("Costela suína", "Linguiça defumada"),
                List.of("Medalhão de frango com bacon", "Asinhas de frango"),
                List.of("Queijo coaclho", "Pão de alho especial", "Arroz branco", "Farofa especial", "Salada verde")
        );

        BBQOption essencial = new BBQOption(
                "CHURRASCO ESSENCIAL",
                List.of("Picanha", "Contra-filé"),
                List.of("Linguiça toscana"),
                List.of("Coração de frango", "Coxa e sobrecoxa"),
                List.of("Arroz branco", "Farofa", "Vinagrete", "Maionese", "Pão de alho")
        );

        return new BBQMenu(premium, essencial);
    }
}
