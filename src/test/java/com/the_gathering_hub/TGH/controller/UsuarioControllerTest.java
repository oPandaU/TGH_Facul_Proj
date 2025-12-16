package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.UsuarioDTO;
import com.the_gathering_hub.TGH.model.Usuario;
import com.the_gathering_hub.TGH.service.UsuarioService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuario.setQtdJogosJogados(0);
        usuario.setQtdReviewsFeitas(0);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setSenha("senha123");
    }

    @Test
    void testListarTodos_DeveRetornarListaDeUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.listarTodos()).thenReturn(usuarios);

        // Act
        ResponseEntity<List<Usuario>> response = usuarioController.listarTodos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(usuarioService, times(1)).listarTodos();
    }

    @Test
    void testBuscarPorId_DeveRetornarUsuario() {
        // Arrange
        when(usuarioService.buscarPorId(1)).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorId(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
        verify(usuarioService, times(1)).buscarPorId(1);
    }

    @Test
    void testBuscarPorId_DeveRetornarNotFound() {
        // Arrange
        when(usuarioService.buscarPorId(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorId(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).buscarPorId(999);
    }

    @Test
    void testBuscarPorEmail_DeveRetornarUsuario() {
        // Arrange
        when(usuarioService.buscarPorEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorEmail("joao@email.com");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).buscarPorEmail("joao@email.com");
    }

    @Test
    void testBuscarPorEmail_DeveRetornarNotFound() {
        // Arrange
        when(usuarioService.buscarPorEmail("inexistente@email.com")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioController.buscarPorEmail("inexistente@email.com");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).buscarPorEmail("inexistente@email.com");
    }

    @Test
    void testCadastrar_DeveCriarNovoUsuario() {
        // Arrange
        when(usuarioService.salvar(any(UsuarioDTO.class))).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.cadastrar(usuarioDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
        verify(usuarioService, times(1)).salvar(any(UsuarioDTO.class));
    }

    @Test
    void testCadastrar_DeveRetornarBadRequestQuandoErro() {
        // Arrange
        when(usuarioService.salvar(any(UsuarioDTO.class))).thenThrow(new RuntimeException("Email já existe"));

        // Act
        ResponseEntity<Usuario> response = usuarioController.cadastrar(usuarioDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).salvar(any(UsuarioDTO.class));
    }

    @Test
    void testAtualizar_DeveAtualizarUsuarioExistente() {
        // Arrange
        when(usuarioService.atualizar(eq(1), any(UsuarioDTO.class))).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.atualizar(1, usuarioDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioService, times(1)).atualizar(eq(1), any(UsuarioDTO.class));
    }

    @Test
    void testAtualizar_DeveRetornarNotFoundParaUsuarioInexistente() {
        // Arrange
        when(usuarioService.atualizar(eq(999), any(UsuarioDTO.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioController.atualizar(999, usuarioDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).atualizar(eq(999), any(UsuarioDTO.class));
    }

    @Test
    void testAtualizar_DeveRetornarBadRequestQuandoErro() {
        // Arrange
        when(usuarioService.atualizar(eq(1), any(UsuarioDTO.class))).thenThrow(new RuntimeException("Email já existe"));

        // Act
        ResponseEntity<Usuario> response = usuarioController.atualizar(1, usuarioDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioService, times(1)).atualizar(eq(1), any(UsuarioDTO.class));
    }

    @Test
    void testExcluir_DeveExcluirUsuarioComSucesso() {
        // Arrange
        when(usuarioService.excluir(1)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = usuarioController.excluir(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).excluir(1);
    }

    @Test
    void testExcluir_DeveRetornarNotFoundParaUsuarioInexistente() {
        // Arrange
        when(usuarioService.excluir(999)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = usuarioController.excluir(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).excluir(999);
    }
}
