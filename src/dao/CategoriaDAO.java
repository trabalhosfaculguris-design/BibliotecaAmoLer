package dao;
import model.Categoria;
import util.Conexao;

import java.sql.*;
import java.util.*;

public class CategoriaDAO implements DAO<Categoria> {

    @Override
    public void inserir(Categoria c) throws Exception {
        try (Connection con = Conexao.getConexao();
        PreparedStatement ps = con.prepareStatement(
        		"INSERT INTO categoria (nome, descricao) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            		ps.setString(1, c.getNome()); ps.setString(2, c.getDescricao());
            		ps.executeUpdate();
            		try (ResultSet rs = ps.getGeneratedKeys()) { 
            			if (rs.next()) c.setId(rs.getInt(1)); 
            		}  		
        }
    }

    @Override
    public void atualizar(Categoria c) throws Exception {
        try (Connection con = Conexao.getConexao();
        PreparedStatement ps = con.prepareStatement("UPDATE categoria SET nome=?, descricao=? WHERE id=?")) {
        	ps.setString(1, c.getNome()); ps.setString(2, c.getDescricao()); ps.setInt(3, c.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try (Connection con = Conexao.getConexao();
        PreparedStatement ps = con.prepareStatement("DELETE FROM categoria WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }

    @Override
    public Categoria buscarPorId(int id) throws Exception {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
            }
        }
        return null;
    }

    @Override
    public List<Categoria> listarTodos() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM categoria ORDER BY nome")) {
            while (rs.next()) lista.add(new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao")));
        }
        return lista;
    }
}
