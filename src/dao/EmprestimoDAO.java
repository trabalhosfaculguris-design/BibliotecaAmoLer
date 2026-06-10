package dao;
import model.*;
import util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements DAO<Emprestimo> {

    @Override
    public void inserir(Emprestimo e) throws Exception {
        String sql = "INSERT INTO emprestimo (id_membro, id_livro, data_emprestimo, data_prevista, status) VALUES (?,?,?,?,?)";
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt   (1, e.getMembro().getId());
            ps.setInt   (2, e.getLivro().getId());
            ps.setDate  (3, Date.valueOf(e.getDataEmprestimo()));
            ps.setDate  (4, Date.valueOf(e.getDataPrevista()));
            ps.setString(5, e.getStatus().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
        
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement("UPDATE livro SET quantidade = quantidade - 1 WHERE id=?")) {
            ps.setInt(1, e.getLivro().getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void atualizar(Emprestimo e) throws Exception {
        String sql = "UPDATE emprestimo SET id_membro=?, id_livro=?, data_emprestimo=?, data_prevista=?, data_devolucao=?, status=? WHERE id=?";
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt   (1, e.getMembro().getId());
            ps.setInt   (2, e.getLivro().getId());
            ps.setDate  (3, Date.valueOf(e.getDataEmprestimo()));
            ps.setDate  (4, Date.valueOf(e.getDataPrevista()));
            ps.setDate  (5, e.getDataDevolucao() != null ? Date.valueOf(e.getDataDevolucao()) : null);
            ps.setString(6, e.getStatus().name());
            ps.setInt   (7, e.getId());
            ps.executeUpdate();
        }
    }

    public void registrarDevolucao(int idEmprestimo) throws Exception {
        String sql = "UPDATE emprestimo SET data_devolucao=?, status='DEVOLVIDO' WHERE id=?";
        int idLivro;
        try (Connection c = Conexao.getConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt (2, idEmprestimo);
            ps.executeUpdate();
        }
        
        try (Connection c = Conexao.getConexao();
            PreparedStatement ps = c.prepareStatement("SELECT id_livro FROM emprestimo WHERE id=?")) {
            	ps.setInt(1, idEmprestimo);
            	try (ResultSet rs = ps.executeQuery()) {
            		if (!rs.next()) return;
            		idLivro = rs.getInt("id_livro");
            }
        }
        
        try (Connection c = Conexao.getConexao();
            PreparedStatement ps = c.prepareStatement("UPDATE livro SET quantidade = quantidade + 1 WHERE id=?")) {
            	ps.setInt(1, idLivro);
            	ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try (Connection c = Conexao.getConexao();
            PreparedStatement ps = c.prepareStatement("DELETE FROM emprestimo WHERE id=?")) {
            	ps.setInt(1, id);
            	ps.executeUpdate();
        }
    }

    @Override
    public Emprestimo buscarPorId(int id) throws Exception {
        String sql = buildQuery() + " WHERE e.id=?";
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        
        return null;
    }

    @Override
    public List<Emprestimo> listarTodos() throws Exception {
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection c = Conexao.getConexao();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(buildQuery() + " ORDER BY e.data_emprestimo DESC")) {
            while (rs.next()) lista.add(mapear(rs));
        }
        
        return lista;
    }

    public List<Emprestimo> listarAtivos() throws Exception {
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection c = Conexao.getConexao();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(buildQuery() + " WHERE e.status IN ('ATIVO','ATRASADO') ORDER BY e.data_prevista")) {
            while (rs.next()) lista.add(mapear(rs));
        }
        
        return lista;
    }

    private String buildQuery() {
        return "SELECT e.*, m.nome as mem_nome, m.cpf as mem_cpf, l.titulo as liv_titulo " + "FROM emprestimo e " + "JOIN membro m ON e.id_membro = m.id " + "JOIN livro  l ON e.id_livro  = l.id";
    }

    private Emprestimo mapear(ResultSet rs) throws SQLException {
        Membro m = new Membro();
        m.setId  (rs.getInt   ("id_membro"));
        m.setNome(rs.getString("mem_nome"));
        m.setCpf (rs.getString("mem_cpf"));

        Livro l = new Livro();
        l.setId    (rs.getInt   ("id_livro"));
        l.setTitulo(rs.getString("liv_titulo"));

        Date dev = rs.getDate("data_devolucao");
        return new Emprestimo(
            rs.getInt    ("id"),
            m, l,
            rs.getDate("data_emprestimo").toLocalDate(),
            rs.getDate("data_prevista").toLocalDate(),
            dev != null ? dev.toLocalDate() : null,
            StatusEmprestimo.valueOf(rs.getString("status"))
        );
    }
}
