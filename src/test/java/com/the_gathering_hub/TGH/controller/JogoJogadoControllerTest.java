package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.JogoJogadoDTO;
import com.the_gathering_hub.TGH.model.JogoJogado;
import com.the_gathering_hub.TGH.service.JogoJogadoService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JogoJogadoControllerTest {

    @Mock
    private JogoJogadoService jogoJogadoService;

    @InjectMocks
    private JogoJogadoController jogoJogadoController;

    private JogoJogado jogoJogado;
    private JogoJogadoDTO jogoJogadoDTO;

    @BeforeEach
    void setUp() {
        jogoJogado = new JogoJogado();
        jogoJogado.setId(1);
        // Não precisamos setar usuario e game nos testes unitários

        jogoJogadoDTO = new JogoJogadoDTO();
        jogoJogadoDTO.setUsuarioId(1);
        jogoJogadoDTO.setGameId(1);
    }

    @Test
    void testListarPorUsuario_DeveRetornarListaDeJogos() {
        // Arrange
        List<JogoJogado> jogos = Arrays.asList(jogoJogado);
        when(jogoJogadoService.listarPorUsuario(1)).thenReturn(jogos);

        // Act
        ResponseEntity<List<JogoJogado>> response = jogoJogadoController.listarPorUsuario(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(jogoJogadoService, times(1)).listarPorUsuario(1);
    }

    @Test
    void testListarPorGame_DeveRetornarListaDeUsuarios() {
        // Arrange
        List<JogoJogado> jogos = Arrays.asList(jogoJogado);
        when(jogoJogadoService.listarPorGame(1)).thenReturn(jogos);

        // Act
        ResponseEntity<List<JogoJogado>> response = jogoJogadoController.listarPorGame(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(jogoJogadoService, times(1)).listarPorGame(1);
    }

    @Test
    void testVerificarSeJogou_DeveRetornarTrueQuandoJogou() {
        // Arrange
        when(jogoJogadoService.jaJogou(1, 1)).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Boolean>> response = jogoJogadoController.verificarSeJogou(1, 1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("jogou"));
        verify(jogoJogadoService, times(1)).jaJogou(1, 1);
    }

    @Test
    void testVerificarSeJogou_DeveRetornarFalseQuandoNaoJogou() {
        // Arrange
        when(jogoJogadoService.jaJogou(1, 1)).thenReturn(false);

        // Act
        ResponseEntity<Map<String, Boolean>> response = jogoJogadoController.verificarSeJogou(1, 1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().get("jogou"));
        verify(jogoJogadoService, times(1)).jaJogou(1, 1);
    }

    @Test
    void testRegistrar_DeveRegistrarJogoJogado() {
        // Arrange
        when(jogoJogadoService.salvar(any(JogoJogadoDTO.class))).thenReturn(jogoJogado);

        // Act
        ResponseEntity<JogoJogado> response = jogoJogadoController.registrar(jogoJogadoDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(jogoJogadoService, times(1)).salvar(any(JogoJogadoDTO.class));
    }

    @Test
    void testRegistrar_DeveRetornarBadRequestQuandoErro() {
        // Arrange
        when(jogoJogadoService.salvar(any(JogoJogadoDTO.class))).thenThrow(new RuntimeException("Jogo já registrado"));

        // Act
        ResponseEntity<JogoJogado> response = jogoJogadoController.registrar(jogoJogadoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(jogoJogadoService, times(1)).salvar(any(JogoJogadoDTO.class));
    }

    @Test
    void testExcluir_DeveExcluirRegistroComSucesso() {
        // Arrange
        when(jogoJogadoService.excluir(1)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = jogoJogadoController.excluir(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(jogoJogadoService, times(1)).excluir(1);
    }

    @Test
    void testExcluir_DeveRetornarNotFoundParaRegistroInexistente() {
        // Arrange
        when(jogoJogadoService.excluir(999)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = jogoJogadoController.excluir(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(jogoJogadoService, times(1)).excluir(999);
    }
}
