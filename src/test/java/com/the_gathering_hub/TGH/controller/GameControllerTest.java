package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.GameDTO;
import com.the_gathering_hub.TGH.model.Game;
import com.the_gathering_hub.TGH.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private Game game;
    private GameDTO gameDTO;

    @BeforeEach
    void setUp() {
        // Configuração de dados de teste
        game = new Game();
        game.setId(1);
        game.setNome("The Witcher 3");
        game.setDataLancamento(LocalDate.of(2015, 5, 19));
        game.setGenero("RPG");
        game.setDeveloper("CD Projekt Red");
        game.setPublisher("CD Projekt");
        game.setDescricao("Um RPG de mundo aberto épico");

        gameDTO = new GameDTO(game);
    }

    @Test
    void testListarTodos_DeveRetornarListaDeGames() {
        // Arrange
        List<Game> games = Arrays.asList(game);
        when(gameService.listarTodos()).thenReturn(games);

        // Act
        ResponseEntity<?> response = gameController.listarTodos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(gameService, times(1)).listarTodos();
    }

    @Test
    void testListarTodos_DeveTratarExcecao() {
        // Arrange
        when(gameService.listarTodos()).thenThrow(new RuntimeException("Erro no banco"));

        // Act
        ResponseEntity<?> response = gameController.listarTodos();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(gameService, times(1)).listarTodos();
    }

    @Test
    void testBuscarPorId_DeveRetornarGame() {
        // Arrange
        when(gameService.buscarPorId(1)).thenReturn(Optional.of(game));

        // Act
        ResponseEntity<Game> response = gameController.buscarPorId(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("The Witcher 3", response.getBody().getNome());
        verify(gameService, times(1)).buscarPorId(1);
    }

    @Test
    void testBuscarPorId_DeveRetornarNotFound() {
        // Arrange
        when(gameService.buscarPorId(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Game> response = gameController.buscarPorId(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(gameService, times(1)).buscarPorId(999);
    }

    @Test
    void testPesquisar_DeveRetornarGamesPorNome() {
        // Arrange
        List<Game> games = Arrays.asList(game);
        when(gameService.pesquisarPorNome("Witcher")).thenReturn(games);

        // Act
        ResponseEntity<List<Game>> response = gameController.pesquisar("Witcher");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(gameService, times(1)).pesquisarPorNome("Witcher");
    }

    @Test
    void testBuscarPorGenero_DeveRetornarGamesPorGenero() {
        // Arrange
        List<Game> games = Arrays.asList(game);
        when(gameService.buscarPorGenero("RPG")).thenReturn(games);

        // Act
        ResponseEntity<List<Game>> response = gameController.buscarPorGenero("RPG");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(gameService, times(1)).buscarPorGenero("RPG");
    }

    @Test
    void testBuscarPorDeveloper_DeveRetornarGamesPorDeveloper() {
        // Arrange
        List<Game> games = Arrays.asList(game);
        when(gameService.buscarPorDeveloper("CD Projekt Red")).thenReturn(games);

        // Act
        ResponseEntity<List<Game>> response = gameController.buscarPorDeveloper("CD Projekt Red");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(gameService, times(1)).buscarPorDeveloper("CD Projekt Red");
    }

    @Test
    void testCadastrar_DeveCriarNovoGame() {
        // Arrange
        when(gameService.salvar(any(GameDTO.class))).thenReturn(game);

        // Act
        ResponseEntity<Game> response = gameController.cadastrar(gameDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("The Witcher 3", response.getBody().getNome());
        verify(gameService, times(1)).salvar(any(GameDTO.class));
    }

    @Test
    void testAtualizar_DeveAtualizarGameExistente() {
        // Arrange
        when(gameService.atualizar(eq(1), any(GameDTO.class))).thenReturn(Optional.of(game));

        // Act
        ResponseEntity<Game> response = gameController.atualizar(1, gameDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(gameService, times(1)).atualizar(eq(1), any(GameDTO.class));
    }

    @Test
    void testAtualizar_DeveRetornarNotFoundParaGameInexistente() {
        // Arrange
        when(gameService.atualizar(eq(999), any(GameDTO.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Game> response = gameController.atualizar(999, gameDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(gameService, times(1)).atualizar(eq(999), any(GameDTO.class));
    }

    @Test
    void testExcluir_DeveExcluirGameComSucesso() {
        // Arrange
        when(gameService.excluir(1)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = gameController.excluir(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(gameService, times(1)).excluir(1);
    }

    @Test
    void testExcluir_DeveRetornarNotFoundParaGameInexistente() {
        // Arrange
        when(gameService.excluir(999)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = gameController.excluir(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(gameService, times(1)).excluir(999);
    }
}
