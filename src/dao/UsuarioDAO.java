package dao;
import model.PerfilUsuario;
import model.UsuarioSistema;
import util.Conexao;

import java.security.MessageDigest;
import java.sql.*;
import java.util.*;

public class UsuarioDAO implements DAO<UsuarioSistema> {

    public UsuarioSistema autenticar(String login, String senha) throws Exception {
        try (Connection con = Conexao.getConexao();
        PreparedStatement ps = con.prepareStatement(
        		"SELECT * FROM usuario_sistema WHERE login=? AND senha=?")) {
            		ps.setString(1, login); ps.setString(2, senha);
            		try (ResultSet rs = ps.executeQuery()) {
            			if (rs.next()) return mapear(rs);
            		}
        		}
        
        return null;
    }

    @Override
    public void inserir(UsuarioSistema u) throws Exception {
        String sql = "INSERT INTO usuario_sistema (login, senha, perfil) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getPerfil().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) u.setId(rs.getInt(1));
            }
        }
    }
    
    @Override
    public void atualizar(UsuarioSistema u) throws Exception {
        boolean alterarSenha = u.getSenha() != null && !u.getSenha().isEmpty();
 
        String sql = alterarSenha
            ? "UPDATE usuario_sistema SET login=?, senha=?, perfil=? WHERE id=?"
            : "UPDATE usuario_sistema SET login=?, perfil=? WHERE id=?";
 
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            if (alterarSenha) {
                ps.setString(2, u.getSenha());
                ps.setString(3, u.getPerfil().name());
                ps.setInt   (4, u.getId());
            } else {
                ps.setString(2, u.getPerfil().name());
                ps.setInt   (3, u.getId());
            }
            ps.executeUpdate();
        }
    }
    
    @Override
    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM usuario_sistema WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public UsuarioSistema buscarPorId(int id) throws Exception {
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario_sistema WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return mapear(rs); }
        }
        return null;
    }

    @Override
    public List<UsuarioSistema> listarTodos() throws Exception {
        List<UsuarioSistema> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM usuario_sistema ORDER BY login")) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    private UsuarioSistema mapear(ResultSet rs) throws SQLException {
        return new UsuarioSistema(
            rs.getInt("id"),
            rs.getString("login"),
            rs.getString("senha"),
            PerfilUsuario.valueOf(rs.getString("perfil"))
        );
    }

}
