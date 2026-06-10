package dao;
import model.Membro;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembroDAO implements DAO<Membro> {

    @Override
    public void inserir(Membro m) throws Exception {
        String sql = "INSERT INTO membro (nome, cpf, email, telefone, endereco, ativo) VALUES (?,?,?,?,?,?)";
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString (1, m.getNome());
            ps.setString (2, m.getCpf());
            ps.setString (3, m.getEmail());
            ps.setString (4, m.getTelefone());
            ps.setString (5, m.getEndereco());
            ps.setBoolean(6, m.isAtivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public void atualizar(Membro m) throws Exception {
        String sql = "UPDATE membro SET nome=?, cpf=?, email=?, telefone=?, endereco=?, ativo=? WHERE id=?";
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString (1, m.getNome());
            ps.setString (2, m.getCpf());
            ps.setString (3, m.getEmail());
            ps.setString (4, m.getTelefone());
            ps.setString (5, m.getEndereco());
            ps.setBoolean(6, m.isAtivo());
            ps.setInt    (7, m.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement("DELETE FROM membro WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Membro buscarPorId(int id) throws Exception {
        try (Connection c = Conexao.getConexao();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM membro WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        
        return null;
    }

    @Override
    public List<Membro> listarTodos() throws Exception {
        List<Membro> lista = new ArrayList<>();
        try (Connection c = Conexao.getConexao();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM membro ORDER BY nome")) {
            while (rs.next()) lista.add(mapear(rs));
        }
        
        return lista;
    }

    private Membro mapear(ResultSet rs) throws SQLException {
        
    	return new Membro(
            rs.getInt    ("id"),
            rs.getString ("nome"),
            rs.getString ("cpf"),
            rs.getString ("email"),
            rs.getString ("telefone"),
            rs.getString ("endereco"),
            rs.getBoolean("ativo")
        );
    }
}
