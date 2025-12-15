package com.the_gathering_hub.TGH.service;

import com.the_gathering_hub.TGH.dto.GameDTO;
import com.the_gathering_hub.TGH.model.Game;
import com.the_gathering_hub.TGH.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> listarTodos() {
        return gameRepository.findAll();
    }

    public Optional<Game> buscarPorId(Integer id) {
        return gameRepository.findById(id);
    }

    public List<Game> pesquisarPorNome(String nome) {
        return gameRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Game> buscarPorGenero(String genero) {
        return gameRepository.findByGenero(genero);
    }

    public List<Game> buscarPorDeveloper(String developer) {
        return gameRepository.findByDeveloperContainingIgnoreCase(developer);
    }

    @Transactional
    public Game salvar(GameDTO gameDTO) {
        Game game = new Game();
        game.setNome(gameDTO.getNome());
        game.setDescricao(gameDTO.getDescricao());
        game.setDataLancamento(LocalDate.parse(gameDTO.getDataLancamento()));
        game.setDeveloper(gameDTO.getDeveloper());
        game.setPublisher(gameDTO.getPublisher());
        game.setGenero(gameDTO.getGenero());
        return gameRepository.save(game);
    }

    @Transactional
    public Optional<Game> atualizar(Integer id, GameDTO gameDTO) {
        return gameRepository.findById(id).map(game -> {
            game.setNome(gameDTO.getNome());
            game.setDescricao(gameDTO.getDescricao());
            game.setDataLancamento(LocalDate.parse(gameDTO.getDataLancamento()));
            game.setDeveloper(gameDTO.getDeveloper());
            game.setPublisher(gameDTO.getPublisher());
            game.setGenero(gameDTO.getGenero());
            return gameRepository.save(game);
        });
    }

    @Transactional
    public boolean excluir(Integer id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
