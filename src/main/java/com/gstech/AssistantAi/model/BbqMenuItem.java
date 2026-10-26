package com.gstech.AssistantAi.model;

import com.gstech.AssistantAi.model.enums.BbqMenuSection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_itens_cardapio_churrasco")
@Getter
@Setter
@NoArgsConstructor
public class BbqMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cardapio_tipo", referencedColumnName = "tipo", nullable = false)
    private BbqMenuDefinition menu;

    @Enumerated(EnumType.STRING)
    @Column(name = "secao", nullable = false)
    private BbqMenuSection section;

    @Column(name = "nome_item", nullable = false)
    private String itemName;

    @Column(name = "ordem", nullable = false)
    private int displayOrder;
}
