
INSERT INTO tb_categoria_bebidas (categoria) VALUES
('Cerveja'),
('Suco');

INSERT INTO tb_bebidas (nome, preco, descricao, categoria_id) VALUES
 ('BRAHMA', 9.00, '600 ml', 1),
 ('SKOL', 9.00, '600 ml', 1),
 ('HEINEKEN', 12.00, '600 ml', 1),
 ('MARACUJA', 8.00, '1 Litro', 2),
 ('LARANJA', 8.00, '1 Litro', 2),
 ('ABACAXI', 8.00, '1 Litro', 2);

INSERT INTO tb_cardapio_churrasco (tipo, titulo, preco_base) VALUES
 ('CHURRASCO_ESSENCIAL', 'CHURRASCO ESSENCIAL', 59.90),
 ('CHURRASCO_PREMIUM', 'CHURRASCO PREMIUM', 79.90);

INSERT INTO tb_itens_cardapio_churrasco (cardapio_tipo, secao, nome_item, ordem) VALUES
 ('CHURRASCO_ESSENCIAL', 'BEEF', 'Picanha', 1),
 ('CHURRASCO_ESSENCIAL', 'BEEF', 'Contra-filé', 2),
 ('CHURRASCO_ESSENCIAL', 'PORK', 'Linguiça toscana', 1),
 ('CHURRASCO_ESSENCIAL', 'CHICKEN', 'Coração de frango', 1),
 ('CHURRASCO_ESSENCIAL', 'CHICKEN', 'Coxa e sobrecoxa', 2),
 ('CHURRASCO_ESSENCIAL', 'SIDE', 'Arroz branco', 1),
 ('CHURRASCO_ESSENCIAL', 'SIDE', 'Farofa', 2),
 ('CHURRASCO_ESSENCIAL', 'SIDE', 'Vinagrete', 3),
 ('CHURRASCO_ESSENCIAL', 'SIDE', 'Maionese', 4),
 ('CHURRASCO_ESSENCIAL', 'SIDE', 'Pão de alho', 5),
 ('CHURRASCO_PREMIUM', 'BEEF', 'Picanha Angus', 1),
 ('CHURRASCO_PREMIUM', 'BEEF', 'Ancho', 2),
 ('CHURRASCO_PREMIUM', 'BEEF', 'Chorizo', 3),
 ('CHURRASCO_PREMIUM', 'PORK', 'Costela suína', 1),
 ('CHURRASCO_PREMIUM', 'PORK', 'Linguiça defumada', 2),
 ('CHURRASCO_PREMIUM', 'CHICKEN', 'Medalhão de frango com bacon', 1),
 ('CHURRASCO_PREMIUM', 'CHICKEN', 'Asinhas de frango', 2),
 ('CHURRASCO_PREMIUM', 'SIDE', 'Queijo coalho', 1),
 ('CHURRASCO_PREMIUM', 'SIDE', 'Pão de alho especial', 2),
 ('CHURRASCO_PREMIUM', 'SIDE', 'Arroz branco', 3),
 ('CHURRASCO_PREMIUM', 'SIDE', 'Farofa especial', 4),
 ('CHURRASCO_PREMIUM', 'SIDE', 'Salada verde', 5);


