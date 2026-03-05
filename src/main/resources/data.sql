
INSERT INTO tb_categoria_bebidas (categoria) VALUES
('Água'),
('Cerveja'),
('Suco'),
('Refrigerante');

INSERT INTO tb_bebidas (nome, preco, descricao, categoria_id) VALUES
 ('AGUA_COM_GAS', 2.50, '500 ml', 1),
 ('AGUA_SEM_GAS', 2.50, '500 ml', 1),
 ('BRAHMA', 9.00, '600 ml', 2),
 ('SKOL', 9.00, '600 ml', 2),
 ('HEINEKEN', 12.00, '600 ml', 2),
 ('MARACUJA', 5.00, '1 Litro', 3),
 ('LARANJA', 5.00, '1 Litro', 3),
 ('ABACAXI', 5.00, '1 Litro', 3),
 ('COCA_COLA', 9.00, '2 Litros', 4),
('GUARANA', 9.00, '2 Litros', 4),
('FANTA', 9.00, '2 Litros', 4);


INSERT INTO tb_carnes (nome, preco, descricao, tipo) VALUES
-- Bovinos
('Picanha', 89.90, 'Corte nobre bovino, suculento e macio - preco por kg', 'BOVINO'),
('Contra-filé', 59.90, 'Carne bovina macia ideal para churrasco - preco por kg', 'BOVINO'),
('Fraldinha', 54.90, 'Corte bovino saboroso e suculento - preco por kg', 'BOVINO'),

-- Suínos
('Costela de porco', 39.90, 'Costela suina temperada e assada lentamente - preco por kg', 'SUINO'),
('Lombo suíno', 42.90, 'Lombo suino macio e bem temperado - preco por kg', 'SUINO'),
('Linguiça toscana', 29.90, 'Linguica suina artesanal para churrasco - preco por kg', 'SUINO'),

-- Aves
('Coração de frango', 34.90, 'Coracao temperado tradicional de churrasco - preco por kg', 'AVE'),
('Frango (coxinha da asa)', 24.90, 'Cortes de frango marinados e assados - preco por kg', 'AVE');