package dao;


import model.Jogo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {

    public void salvar(Jogo jogo) {
        String sql = "INSERT INTO jogos(nome, descricao, data_lancamento, desenvolvedora, publicadora) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogo.getNome());
            stmt.setString(2, jogo.getDescricao());
            stmt.setDate(3, Date.valueOf(jogo.getDataLancamento()));
            stmt.setString(4, jogo.getDesenvolvedora());
            stmt.setString(5, jogo.getPublicadora());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Jogo> listarTodos() {
        List<Jogo> lista = new ArrayList<>();
        String sql = "SELECT * FROM jogos";
        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jogo j = new Jogo(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_lancamento").toLocalDate().toString(),
                        rs.getString("desenvolvedora"),
                        rs.getString("publicadora")
                );
                j.setId(rs.getInt("id"));
                lista.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
