package com.the_gathering_hub.TGH.service;

import com.the_gathering_hub.TGH.dto.JogoJogadoDTO;
import com.the_gathering_hub.TGH.model.Game;
import com.the_gathering_hub.TGH.model.JogoJogado;
import com.the_gathering_hub.TGH.model.Usuario;
import com.the_gathering_hub.TGH.repository.GameRepository;
import com.the_gathering_hub.TGH.repository.JogoJogadoRepository;
import com.the_gathering_hub.TGH.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JogoJogadoService {

    private final JogoJogadoRepository jogoJogadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GameRepository gameRepository;

    @Autowired
    public JogoJogadoService(JogoJogadoRepository jogoJogadoRepository,
            UsuarioRepository usuarioRepository,
            GameRepository gameRepository) {
        this.jogoJogadoRepository = jogoJogadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.gameRepository = gameRepository;
    }

    public List<JogoJogado> listarPorUsuario(Integer usuarioId) {
        return jogoJogadoRepository.findByUsuarioId(usuarioId);
    }

    public List<JogoJogado> listarPorGame(Integer gameId) {
        return jogoJogadoRepository.findByGameId(gameId);
    }

    public boolean jaJogou(Integer usuarioId, Integer gameId) {
        return jogoJogadoRepository.existsByUsuarioIdAndGameId(usuarioId, gameId);
    }

    @Transactional
    public JogoJogado salvar(JogoJogadoDTO jogoJogadoDTO) {
        Usuario usuario = usuarioRepository.findById(jogoJogadoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Game game = gameRepository.findById(jogoJogadoDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Game não encontrado"));

        if (jogoJogadoRepository.existsByUsuarioIdAndGameId(usuario.getId(), game.getId())) {
            throw new RuntimeException("Usuário já registrou este jogo como jogado");
        }

        JogoJogado jogoJogado = new JogoJogado();
        jogoJogado.setUsuario(usuario);
        jogoJogado.setGame(game);

        if (jogoJogadoDTO.getDataJogada() != null && !jogoJogadoDTO.getDataJogada().isEmpty()) {
            jogoJogado.setDataJogada(LocalDate.parse(jogoJogadoDTO.getDataJogada()));
        } else {
            jogoJogado.setDataJogada(LocalDate.now());
        }

        return jogoJogadoRepository.save(jogoJogado);
    }

    @Transactional
    public boolean excluir(Integer id) {
        if (jogoJogadoRepository.existsById(id)) {
            jogoJogadoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
