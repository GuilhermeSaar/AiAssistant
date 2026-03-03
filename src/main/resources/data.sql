

INSERT INTO tb_bebidas (nome, preco, descricao) VALUES
 ('Água', 1.20, '500 ml'),
 ('Cerveja', 6.50, '600 ml'),
 ('Suco', 3.50, '1 Litro'),
 ('Refrigerante', 8.00, '2 Litros');

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