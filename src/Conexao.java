
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection conn;

    public static Connection getConexao() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/jogos_reviews";
                String usuario = "root";
                String senha = "12345";

                conn = DriverManager.getConnection(url, usuario, senha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco.");
        }
        return conn;
    }
}
