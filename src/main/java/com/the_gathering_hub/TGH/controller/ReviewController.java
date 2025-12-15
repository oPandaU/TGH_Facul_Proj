package com.the_gathering_hub.TGH.controller;

import com.the_gathering_hub.TGH.dto.ReviewDTO;
import com.the_gathering_hub.TGH.model.Review;
import com.the_gathering_hub.TGH.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Review>> listarPorGame(@PathVariable Integer gameId) {
        return ResponseEntity.ok(reviewService.listarPorGame(gameId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Review>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(reviewService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/game/{gameId}/media")
    public ResponseEntity<Map<String, Double>> obterMediaNotas(@PathVariable Integer gameId) {
        Double media = reviewService.obterMediaNotas(gameId);
        Map<String, Double> response = new HashMap<>();
        response.put("media", media != null ? media : 0.0);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Review> cadastrar(@RequestBody ReviewDTO reviewDTO) {
        try {
            Review review = reviewService.salvar(reviewDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> atualizar(@PathVariable Integer id, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.atualizar(id, reviewDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (reviewService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> excluirTodasPorGame(@PathVariable Integer gameId) {
        reviewService.excluirTodasPorGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
