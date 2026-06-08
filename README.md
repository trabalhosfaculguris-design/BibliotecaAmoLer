# Sistema de Gerenciamento de Biblioteca

## Disciplina: Programação Avançada (184987) — UNIASSELVI
**Professor:** Everton Pereira da Cruz  
**Entrega máxima:** 12/06/2026
**Alunos:** Carlos Daniel dos Santos, Erik Uller e Rômulo Ranieri Vargas

---

## Visão Geral

Sistema desktop em **Java 8 + Swing + MySQL** para gerenciar o acervo e os empréstimos de uma biblioteca.  
O sistema cobre as necessidades reais de:
- Cadastro de livros, categorias e autores
- Cadastro de membros (leitores)
- Registro e devolução de empréstimos
- Controle de estoque automático
- Autenticação de operadores (perfis ADMIN / ATENDENTE)

---

## Arquitetura em Camadas

```
view/         → Interface gráfica Swing (JFrame, JPanel, JDialog)
dao/          → Acesso ao banco de dados MySQL (JDBC) — implementam DAO<T>
model/        → Entidades, enumerações e interface Persistivel
util/         → Utilitários (Conexao JDBC)
sql/          → Scripts DDL e DML
```

### Relação com os capítulos do livro
| Assunto | Capítulo (Furgeri, 2015) |
|---------|--------------------------|
| Interface gráfica Swing (JFrame, JPanel, JDialog) | Cap. 8 |
| Eventos (ActionListener, WindowListener) | Cap. 9 |
| Conexão MySQL / JDBC / PreparedStatement | Cap. 12 |

---

## Responsabilidades por integrantes:

Carlos:
- Diagrama UML;
- Banco de dados;
- Classes do package 'dao';

Erik:
- Classes do package 'view';
- Classe de conexão com o banco;
- Readme;

Romulo:
- Classes do package 'model';
- Analise de negocio;

---

## Relacionamentos UML

- **Realização (tracejado + triângulo):** `Livro`, `Membro`, `Categoria`, `Autor`, `Emprestimo`, `UsuarioSistema` → `Persistivel`
- **Realização:** `LivroDAO`, `MembroDAO`, `EmprestimoDAO`, `CategoriaDAO`, `UsuarioDAO` → `DAO<T>`
- **Agregação:** `Livro` agrega `Categoria` (0..1) e lista de `Autor` (0..*)
- **Associação:** `Emprestimo` associa `Membro` (1) e `Livro` (1)
- **Dependência de uso:** `Emprestimo` usa `StatusEmprestimo`; `UsuarioSistema` usa `PerfilUsuario`
- **Dependência de uso:** todos os DAOs usam `Conexao`

---

## Configuração e Execução

### Pré-requisitos
- Java 8+
- Eclipse IDE 2025-03
- MySQL 8+ (localhost:3306)
- Conector MySQL: `mysql-connector-j-8.x.x.jar` no classpath

### Passos
1. Execute `sql/01_criar_banco.sql` no MySQL Workbench
2. Execute `sql/02_popular_banco.sql`
3. Abra o projeto no Eclipse: File → Import → Existing Projects into Workspace
4. Adicione o JAR do conector ao Build Path
5. Execute `view.TelaPrincipal` como Java Application
6. Login padrão: `admin` / `admin123`

---

## GitHub
- Repositório do projeto: `https://github.com/<equipe>/biblioteca-java`
- Branch `main`: versão estável
- Branch `membro1`, `membro2`, `membro3`: desenvolvimento individual

---

## Fontes externas consultadas
- Furgeri, Sérgio. *Java 8 - ensino didático*. São Paulo: Érica, 2015.
  - Cap. 8 (Interfaces gráficas), Cap. 9 (Eventos), Cap. 12 (JDBC/MySQL)
- Documentação Oracle Java SE 8: https://docs.oracle.com/javase/8/docs/api/
