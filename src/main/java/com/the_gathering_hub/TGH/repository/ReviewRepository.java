package com.the_gathering_hub.TGH.repository;

import com.the_gathering_hub.TGH.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByGameId(Integer gameId);

    List<Review> findByUsuarioId(Integer usuarioId);

    @Query("SELECT AVG(r.nota) FROM Review r WHERE r.game.id = ?1")
    Double findMediaNotasByGameId(Integer gameId);

    void deleteByGameId(Integer gameId);
}
