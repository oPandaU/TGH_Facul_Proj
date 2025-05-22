package dao;

import model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public void salvar(Review review, int jogoId) {
        String sql = "INSERT INTO reviews(jogo_id, texto) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogoId);
            stmt.setString(2, review.getTexto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> listarPorJogo(int jogoId) {
        List<Review> lista = new ArrayList<>();
        String sql = "SELECT texto FROM reviews WHERE jogo_id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Review("", rs.getString("texto")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void excluirTodasReviews(int jogoId) {
        String sql = "DELETE FROM reviews WHERE jogo_id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirPrimeiraReview(int jogoId) {
        String sql = "DELETE FROM reviews WHERE jogo_id = ? ORDER BY id LIMIT 1";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
