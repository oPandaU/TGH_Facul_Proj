package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.GameDTO;
import com.the_gathering_hub.TGH.model.Game;
import com.the_gathering_hub.TGH.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /*
    @GetMapping
    public ResponseEntity<List<Game>> listarTodos() {
        return ResponseEntity.ok(gameService.listarTodos());
    }
     */

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(gameService.listarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> buscarPorId(@PathVariable Integer id) {
        return gameService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Game>> pesquisar(@RequestParam String nome) {
        return ResponseEntity.ok(gameService.pesquisarPorNome(nome));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Game>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(gameService.buscarPorGenero(genero));
    }

    @GetMapping("/developer")
    public ResponseEntity<List<Game>> buscarPorDeveloper(@RequestParam String nome) {
        return ResponseEntity.ok(gameService.buscarPorDeveloper(nome));
    }

    @PostMapping
    public ResponseEntity<Game> cadastrar(@RequestBody GameDTO gameDTO) {
        Game game = gameService.salvar(gameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> atualizar(@PathVariable Integer id, @RequestBody GameDTO gameDTO) {
        return gameService.atualizar(id, gameDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (gameService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
