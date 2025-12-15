package com.the_gathering_hub.TGH.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jogo_jogado")
public class JogoJogado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "games_id", nullable = false)
    @JsonIgnore
    private Game game;

    @Column(name = "data_jogada")
    private LocalDate dataJogada;

    public JogoJogado() {
    }

    public JogoJogado(Usuario usuario, Game game, LocalDate dataJogada) {
        this.usuario = usuario;
        this.game = game;
        this.dataJogada = dataJogada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getDataJogada() {
        return dataJogada;
    }

    public void setDataJogada(LocalDate dataJogada) {
        this.dataJogada = dataJogada;
    }

    @Transient
    public String getUsuarioNome() {
        return usuario != null ? usuario.getNome() : null;
    }

    @Transient
    public String getGameNome() {
        return game != null ? game.getNome() : null;
    }
}
