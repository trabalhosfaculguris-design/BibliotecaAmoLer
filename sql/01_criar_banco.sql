CREATE DATABASE IF NOT EXISTS biblioteca_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE biblioteca_db;

CREATE TABLE IF NOT EXISTS categoria (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    descricao  VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS autor (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(150) NOT NULL,
    email      VARCHAR(150),
    pais       VARCHAR(80)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS livro (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    isbn            VARCHAR(20) UNIQUE,
    ano_publicacao  INT,
    editora         VARCHAR(150),
    quantidade      INT NOT NULL DEFAULT 0,
    id_categoria    INT,
    CONSTRAINT fk_livro_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS livro_autor (
    id_livro  INT NOT NULL,
    id_autor  INT NOT NULL,
    PRIMARY KEY (id_livro, id_autor),
    CONSTRAINT fk_la_livro FOREIGN KEY (id_livro) REFERENCES livro(id)  ON DELETE CASCADE,
    CONSTRAINT fk_la_autor FOREIGN KEY (id_autor) REFERENCES autor(id)  ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS membro (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(150) NOT NULL,
    cpf        VARCHAR(14) UNIQUE NOT NULL,
    email      VARCHAR(150),
    telefone   VARCHAR(20),
    endereco   VARCHAR(255),
    ativo      TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS emprestimo (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_membro       INT NOT NULL,
    id_livro        INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_prevista   DATE NOT NULL,
    data_devolucao  DATE,
    status          ENUM('ATIVO','DEVOLVIDO','ATRASADO') NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT fk_emp_membro FOREIGN KEY (id_membro) REFERENCES membro(id),
    CONSTRAINT fk_emp_livro  FOREIGN KEY (id_livro)  REFERENCES livro(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS usuario_sistema (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    login    VARCHAR(80) UNIQUE NOT NULL,
    senha    VARCHAR(255) NOT NULL,
    perfil   ENUM('ADMIN','ATENDENTE') NOT NULL DEFAULT 'ATENDENTE'
) ENGINE=InnoDB;
