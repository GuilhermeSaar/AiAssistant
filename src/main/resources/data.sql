
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
 ('CHURRASCO_ESSENCIAL', 'beef', 'Picanha', 1),
 ('CHURRASCO_ESSENCIAL', 'beef', 'Contra-filé', 2),
 ('CHURRASCO_ESSENCIAL', 'pork', 'Linguiça toscana', 1),
 ('CHURRASCO_ESSENCIAL', 'chicken', 'Coração de frango', 1),
 ('CHURRASCO_ESSENCIAL', 'chicken', 'Coxa e sobrecoxa', 2),
 ('CHURRASCO_ESSENCIAL', 'side', 'Arroz branco', 1),
 ('CHURRASCO_ESSENCIAL', 'side', 'Farofa', 2),
 ('CHURRASCO_ESSENCIAL', 'side', 'Vinagrete', 3),
 ('CHURRASCO_ESSENCIAL', 'side', 'Maionese', 4),
 ('CHURRASCO_ESSENCIAL', 'side', 'Pão de alho', 5),
 ('CHURRASCO_PREMIUM', 'beef', 'Picanha Angus', 1),
 ('CHURRASCO_PREMIUM', 'beef', 'Ancho', 2),
 ('CHURRASCO_PREMIUM', 'beef', 'Chorizo', 3),
 ('CHURRASCO_PREMIUM', 'pork', 'Costela suína', 1),
 ('CHURRASCO_PREMIUM', 'pork', 'Linguiça defumada', 2),
 ('CHURRASCO_PREMIUM', 'chicken', 'Medalhão de frango com bacon', 1),
 ('CHURRASCO_PREMIUM', 'chicken', 'Asinhas de frango', 2),
 ('CHURRASCO_PREMIUM', 'side', 'Queijo coalho', 1),
 ('CHURRASCO_PREMIUM', 'side', 'Pão de alho especial', 2),
 ('CHURRASCO_PREMIUM', 'side', 'Arroz branco', 3),
 ('CHURRASCO_PREMIUM', 'side', 'Farofa especial', 4),
 ('CHURRASCO_PREMIUM', 'side', 'Salada verde', 5);


