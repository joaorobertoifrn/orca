package ifrn.persistencia;

import static java.lang.Class.forName;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;

public class Conexao {

    private static final Conexao instancia = new Conexao();

    public static Conexao getInstancia() {
        return instancia;
    }

    private Conexao() {
    }

    public Connection criaConexao() {
   	
    	String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    	String DATABASE_URL = "jdbc:mysql://localhost:3306/sinapi";
        String USERNAME = "root";
    	String PASSWORD = "1234";
    	
    	Connection conn = null;
    	 
    	try {
            forName(DATABASE_DRIVER);
            out.println("Driver registrado.");
            conn = getConnection(
                    DATABASE_URL, 
                    USERNAME, 
                    PASSWORD);
            out.println("Conexão efetuada.");
        } catch (ClassNotFoundException ex) {
            out.println("Driver do banco nao encontrado.");
        } catch (SQLException ex) {
            out.print("Erro ao obter conexao: " + ex.getMessage());
        }
	return conn;
    }
}