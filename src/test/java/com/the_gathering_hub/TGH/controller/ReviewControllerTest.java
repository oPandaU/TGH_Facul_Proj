package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.ReviewDTO;
import com.the_gathering_hub.TGH.model.Review;
import com.the_gathering_hub.TGH.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setId(1);
        review.setNota(9);
        review.setAvaliacao("Excelente jogo!");

        reviewDTO = new ReviewDTO();
        reviewDTO.setNota(9);
        reviewDTO.setAvaliacao("Excelente jogo!");
        reviewDTO.setUsuarioId(1);
        reviewDTO.setGameId(1);
    }

    @Test
    void testListarPorGame_DeveRetornarListaDeReviews() {
        // Arrange
        List<Review> reviews = Arrays.asList(review);
        when(reviewService.listarPorGame(1)).thenReturn(reviews);

        // Act
        ResponseEntity<List<Review>> response = reviewController.listarPorGame(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(reviewService, times(1)).listarPorGame(1);
    }

    @Test
    void testListarPorUsuario_DeveRetornarListaDeReviews() {
        // Arrange
        List<Review> reviews = Arrays.asList(review);
        when(reviewService.listarPorUsuario(1)).thenReturn(reviews);

        // Act
        ResponseEntity<List<Review>> response = reviewController.listarPorUsuario(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(reviewService, times(1)).listarPorUsuario(1);
    }

    @Test
    void testObterMediaNotas_DeveRetornarMediaCalculada() {
        // Arrange
        when(reviewService.obterMediaNotas(1)).thenReturn(9.0);

        // Act
        ResponseEntity<Map<String, Double>> response = reviewController.obterMediaNotas(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(9.0, response.getBody().get("media"));
        verify(reviewService, times(1)).obterMediaNotas(1);
    }

    @Test
    void testObterMediaNotas_DeveRetornarZeroQuandoNull() {
        // Arrange
        when(reviewService.obterMediaNotas(1)).thenReturn(null);

        // Act
        ResponseEntity<Map<String, Double>> response = reviewController.obterMediaNotas(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0.0, response.getBody().get("media"));
        verify(reviewService, times(1)).obterMediaNotas(1);
    }

    @Test
    void testCadastrar_DeveCriarNovaReview() {
        // Arrange
        when(reviewService.salvar(any(ReviewDTO.class))).thenReturn(review);

        // Act
        ResponseEntity<Review> response = reviewController.cadastrar(reviewDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(9, response.getBody().getNota());
        verify(reviewService, times(1)).salvar(any(ReviewDTO.class));
    }

    @Test
    void testCadastrar_DeveRetornarBadRequestQuandoErro() {
        // Arrange
        when(reviewService.salvar(any(ReviewDTO.class))).thenThrow(new RuntimeException("Usuário já fez review"));

        // Act
        ResponseEntity<Review> response = reviewController.cadastrar(reviewDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(reviewService, times(1)).salvar(any(ReviewDTO.class));
    }

    @Test
    void testAtualizar_DeveAtualizarReviewExistente() {
        // Arrange
        when(reviewService.atualizar(eq(1), any(ReviewDTO.class))).thenReturn(Optional.of(review));

        // Act
        ResponseEntity<Review> response = reviewController.atualizar(1, reviewDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(reviewService, times(1)).atualizar(eq(1), any(ReviewDTO.class));
    }

    @Test
    void testAtualizar_DeveRetornarNotFoundParaReviewInexistente() {
        // Arrange
        when(reviewService.atualizar(eq(999), any(ReviewDTO.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Review> response = reviewController.atualizar(999, reviewDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reviewService, times(1)).atualizar(eq(999), any(ReviewDTO.class));
    }

    @Test
    void testExcluir_DeveExcluirReviewComSucesso() {
        // Arrange
        when(reviewService.excluir(1)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = reviewController.excluir(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).excluir(1);
    }

    @Test
    void testExcluir_DeveRetornarNotFoundParaReviewInexistente() {
        // Arrange
        when(reviewService.excluir(999)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = reviewController.excluir(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reviewService, times(1)).excluir(999);
    }

    @Test
    void testExcluirTodasPorGame_DeveExcluirTodasReviews() {
        // Arrange
        doNothing().when(reviewService).excluirTodasPorGame(1);

        // Act
        ResponseEntity<Void> response = reviewController.excluirTodasPorGame(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).excluirTodasPorGame(1);
    }
}
