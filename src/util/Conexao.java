package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String URL = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=America/Sao_Paulo";

    private Conexao() {}

    public static Connection getConexao() throws SQLException 
    {
        try 
        {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(URL, "root", "root");
    }

    public static void fechar(Connection conn) 
    {
        if (conn != null) {
            try 
            { 
            	conn.close(); 
            } 
            catch (SQLException ex) 
            {
            	
            }
        }
    }
}
