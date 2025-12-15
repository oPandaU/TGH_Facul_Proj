package com.the_gathering_hub.TGH.repository;

import com.the_gathering_hub.TGH.model.JogoJogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JogoJogadoRepository extends JpaRepository<JogoJogado, Integer> {

    List<JogoJogado> findByUsuarioId(Integer usuarioId);

    List<JogoJogado> findByGameId(Integer gameId);

    boolean existsByUsuarioIdAndGameId(Integer usuarioId, Integer gameId);
}
