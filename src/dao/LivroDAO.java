package dao;
import model.Categoria;
import model.Livro;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements DAO<Livro> {

    @Override
    public void inserir(Livro livro) throws Exception {
        String sql = "INSERT INTO livro (titulo, isbn, ano_publicacao, editora, quantidade, id_categoria) VALUES (?,?,?,?,?,?)";
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getIsbn());
            ps.setInt   (3, livro.getAnoPublicacao());
            ps.setString(4, livro.getEditora());
            ps.setInt   (5, livro.getQuantidade());
            ps.setObject(6, livro.getCategoria() != null ? livro.getCategoria().getId() : null);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
            	if (rs.next()) livro.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public void atualizar(Livro livro) throws Exception {
        String sql = "UPDATE livro SET titulo=?, isbn=?, ano_publicacao=?, editora=?, quantidade=?, id_categoria=? WHERE id=?";
        try (Connection c = Conexao.getConexao();
            PreparedStatement ps = c.prepareStatement(sql)) {
            	ps.setString(1, livro.getTitulo());
            	ps.setString(2, livro.getIsbn());
            	ps.setInt   (3, livro.getAnoPublicacao());
            	ps.setString(4, livro.getEditora());
            	ps.setInt   (5, livro.getQuantidade());
            	ps.setObject(6, livro.getCategoria() != null ? livro.getCategoria().getId() : null);
            	ps.setInt   (7, livro.getId());
            	ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try (Connection c = Conexao.getConexao();
            PreparedStatement ps = c.prepareStatement("DELETE FROM livro WHERE id=?")) {
        		ps.setInt(1, id);
        		ps.executeUpdate();
        }
    }

    @Override
    public Livro buscarPorId(int id) throws Exception {
        String sql = "SELECT l.*, c.nome as cat_nome, c.descricao as cat_desc " + "FROM livro l LEFT JOIN categoria c ON l.id_categoria = c.id WHERE l.id=?";
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
    public List<Livro> listarTodos() throws Exception {
        String sql = "SELECT l.*, c.nome as cat_nome, c.descricao as cat_desc " + "FROM livro l LEFT JOIN categoria c ON l.id_categoria = c.id ORDER BY l.titulo";
        List<Livro> lista = new ArrayList<>();
        try (Connection c = Conexao.getConexao();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        
        return lista;
    }

    public List<Livro> buscarPorTitulo(String titulo) throws Exception {
        String sql = "SELECT l.*, c.nome as cat_nome, c.descricao as cat_desc " + "FROM livro l LEFT JOIN categoria c ON l.id_categoria = c.id " + "WHERE l.titulo LIKE ? ORDER BY l.titulo";
        List<Livro> lista = new ArrayList<>();
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        
        return lista;
    }

    private Livro mapear(ResultSet rs) throws SQLException {
        Categoria cat = null;
        int catId = rs.getInt("id_categoria");
        if (!rs.wasNull()) {
            cat = new Categoria(catId, rs.getString("cat_nome"), rs.getString("cat_desc"));
        }
        
        return new Livro(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("isbn"),
            rs.getInt("ano_publicacao"),
            rs.getString("editora"),
            rs.getInt("quantidade"),
            cat
        );
    }
}
