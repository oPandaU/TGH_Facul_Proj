package com.the_gathering_hub.TGH.repository;

import com.the_gathering_hub.TGH.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findByNomeContainingIgnoreCase(String nome);

    List<Game> findByGenero(String genero);

    List<Game> findByDeveloperContainingIgnoreCase(String developer);
}
