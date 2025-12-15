package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.JogoJogadoDTO;
import com.the_gathering_hub.TGH.model.JogoJogado;
import com.the_gathering_hub.TGH.service.JogoJogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jogos-jogados")
@CrossOrigin(origins = "*")
public class JogoJogadoController {

    private final JogoJogadoService jogoJogadoService;

    @Autowired
    public JogoJogadoController(JogoJogadoService jogoJogadoService) {
        this.jogoJogadoService = jogoJogadoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<JogoJogado>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(jogoJogadoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<JogoJogado>> listarPorGame(@PathVariable Integer gameId) {
        return ResponseEntity.ok(jogoJogadoService.listarPorGame(gameId));
    }

    @GetMapping("/verificar")
    public ResponseEntity<Map<String, Boolean>> verificarSeJogou(
            @RequestParam Integer usuarioId,
            @RequestParam Integer gameId) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("jogou", jogoJogadoService.jaJogou(usuarioId, gameId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<JogoJogado> registrar(@RequestBody JogoJogadoDTO jogoJogadoDTO) {
        try {
            JogoJogado jogoJogado = jogoJogadoService.salvar(jogoJogadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(jogoJogado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (jogoJogadoService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
