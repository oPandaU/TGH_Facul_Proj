package com.the_gathering_hub.TGH.service;

import com.the_gathering_hub.TGH.dto.ReviewDTO;
import com.the_gathering_hub.TGH.model.Game;
import com.the_gathering_hub.TGH.model.Review;
import com.the_gathering_hub.TGH.model.Usuario;
import com.the_gathering_hub.TGH.repository.GameRepository;
import com.the_gathering_hub.TGH.repository.ReviewRepository;
import com.the_gathering_hub.TGH.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, GameRepository gameRepository, UsuarioRepository usuarioRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Review> listarPorGame(Integer gameId) {
        return reviewRepository.findByGameId(gameId);
    }

    public List<Review> listarPorUsuario(Integer usuarioId) {
        return reviewRepository.findByUsuarioId(usuarioId);
    }

    public Double obterMediaNotas(Integer gameId) {
        return reviewRepository.findMediaNotasByGameId(gameId);
    }

    @Transactional
    public Review salvar(ReviewDTO reviewDTO) {
        Usuario usuario = usuarioRepository.findById(reviewDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Game game = gameRepository.findById(reviewDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Game não encontrado"));

        Review review = new Review();
        review.setUsuario(usuario);
        review.setGame(game);
        review.setNota(reviewDTO.getNota());
        review.setAvaliacao(reviewDTO.getAvaliacao());

        if (reviewDTO.getDataReview() != null && !reviewDTO.getDataReview().isEmpty()) {
            review.setDataReview(LocalDate.parse(reviewDTO.getDataReview()));
        } else {
            review.setDataReview(LocalDate.now());
        }

        return reviewRepository.save(review);
    }

    @Transactional
    public Optional<Review> atualizar(Integer id, ReviewDTO reviewDTO) {
        return reviewRepository.findById(id).map(review -> {
            review.setNota(reviewDTO.getNota());
            review.setAvaliacao(reviewDTO.getAvaliacao());

            if (reviewDTO.getDataReview() != null && !reviewDTO.getDataReview().isEmpty()) {
                review.setDataReview(LocalDate.parse(reviewDTO.getDataReview()));
            }

            return reviewRepository.save(review);
        });
    }

    @Transactional
    public boolean excluir(Integer id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void excluirTodasPorGame(Integer gameId) {
        reviewRepository.deleteByGameId(gameId);
    }
}
