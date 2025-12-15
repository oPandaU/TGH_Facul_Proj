package com.the_gathering_hub.TGH.service;

import com.the_gathering_hub.TGH.dto.UsuarioDTO;
import com.the_gathering_hub.TGH.model.Usuario;
import com.the_gathering_hub.TGH.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public Usuario salvar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha()); // Em produção, usar BCrypt
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Optional<Usuario> atualizar(Integer id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioDTO.getNome());

            if (!usuario.getEmail().equals(usuarioDTO.getEmail())) {
                if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                    throw new RuntimeException("Email já cadastrado");
                }
                usuario.setEmail(usuarioDTO.getEmail());
            }

            if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
                usuario.setSenha(usuarioDTO.getSenha());
            }

            return usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public boolean excluir(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
