USE biblioteca_db;

INSERT INTO categoria (nome, descricao) VALUES
  ('Tecnologia',    'Livros de programação, redes e TI'),
  ('Ciência',       'Física, Química, Biologia'),
  ('Literatura',    'Romances, contos, poesia'),
  ('Engenharia',    'Engenharia civil, mecânica, elétrica'),
  ('Administração', 'Gestão, marketing, finanças');

INSERT INTO autor (nome, email, pais) VALUES
  ('Sérgio Furgeri',      'furgeri@example.com',   'Brasil'),
  ('Martin Fowler',       'fowler@example.com',    'Reino Unido'),
  ('Robert C. Martin',    'uncle.bob@example.com', 'EUA'),
  ('Erich Gamma',         'gamma@example.com',     'Alemanha'),
  ('Joshua Bloch',        'bloch@example.com',     'EUA');

INSERT INTO livro (titulo, isbn, ano_publicacao, editora, quantidade, id_categoria) VALUES
  ('Java 8 - Ensino Didático',    '9788536519340', 2015, 'Érica',     5, 1),
  ('Refactoring',                 '9780201485677', 1999, 'Addison',   3, 1),
  ('Clean Code',                  '9780132350884', 2008, 'Prentice',  4, 1),
  ('Design Patterns',             '9780201633610', 1994, 'Addison',   2, 1),
  ('Effective Java',              '9780134685991', 2018, 'Addison',   3, 1);

INSERT INTO livro_autor (id_livro, id_autor) VALUES
  (1,1),(2,2),(3,3),(4,4),(4,5),(5,5);

INSERT INTO membro (nome, cpf, email, telefone, endereco) VALUES
  ('Ana Souza',       '111.222.333-44', 'ana@email.com',    '(47) 99000-0001', 'Rua das Flores, 10 - Blumenau'),
  ('Bruno Lima',      '222.333.444-55', 'bruno@email.com',  '(47) 99000-0002', 'Av. Brasil, 200 - Blumenau'),
  ('Carla Martins',   '333.444.555-66', 'carla@email.com',  '(47) 99000-0003', 'Rua 7 de Setembro, 50 - Blumenau');

INSERT INTO usuario_sistema (login, senha, perfil) VALUES
  ('admin', 'teste', 'ADMIN'),
  ('atendente','teste', 'ATENDENTE');

INSERT INTO emprestimo (id_membro, id_livro, data_emprestimo, data_prevista, status) VALUES
  (1, 1, CURDATE() - INTERVAL 5 DAY, CURDATE() + INTERVAL 9 DAY,  'ATIVO'),
  (2, 3, CURDATE() - INTERVAL 20 DAY, CURDATE() - INTERVAL 6 DAY, 'ATRASADO'),
  (3, 2, CURDATE() - INTERVAL 15 DAY, CURDATE() - INTERVAL 1 DAY, 'DEVOLVIDO');

UPDATE emprestimo SET data_devolucao = CURDATE() - INTERVAL 2 DAY WHERE id = 3;
