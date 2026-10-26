package com.gstech.AssistantAi.tools;

import com.gstech.AssistantAi.dto.BBQMenu;
import com.gstech.AssistantAi.dto.DrinkMenu;
import com.gstech.AssistantAi.service.MenuService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MenuToolsService {

    private final MenuService menuService;

    public MenuToolsService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Tool("""
            Busca o cardápio oficial de bebidas da Brasa's Churrascaria, incluindo sucos naturais e cervejas disponíveis para os clientes.
            """)
    public DrinkMenu menuDrinks() {

        return new DrinkMenu(menuService.getBeerMenu(), menuService.getJuiceMenu());
    }

    @Tool("""
            Busca o cardápio oficial da Brasa's Churrascaria, contendo os itens detalhados dos serviços Premium e Essencial.
            """)
    public BBQMenu bbqMenu() {

        return new BBQMenu(menuService.getBbqPremiumMenu(), menuService.getBbqEssencialMenu());
    }
}
