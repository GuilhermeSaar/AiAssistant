package com.gstech.AssistantAi.model;

import com.gstech.AssistantAi.model.enums.BBQ;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_cardapio_churrasco")
@Getter
@Setter
@NoArgsConstructor
public class BbqMenuDefinition {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private BBQ type;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(name = "preco_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<BbqMenuItem> items = new ArrayList<>();
}
