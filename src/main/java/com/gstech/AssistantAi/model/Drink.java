package com.gstech.AssistantAi.model;

import com.gstech.AssistantAi.model.enums.NameDrink;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_bebidas")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @Enumerated(EnumType.STRING)
    private NameDrink nameDrink;
    @Column(name = "preco")
    private BigDecimal price;
    @Column(name = "descricao")
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoryDrink category;
}
