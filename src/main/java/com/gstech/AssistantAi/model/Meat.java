package com.gstech.AssistantAi.model;

import com.gstech.AssistantAi.model.enums.MeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_carnes")
public class Meat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;
    @Column(name = "preco")
    private Double price;
    @Column(name = "descricao")
    private String description;
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private MeatType meatType;

}

