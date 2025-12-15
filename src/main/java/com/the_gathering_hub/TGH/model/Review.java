package com.the_gathering_hub.TGH.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {

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

    @Column(nullable = false)
    private Integer nota;

    @Column(columnDefinition = "TEXT")
    private String avaliacao;

    @Column(name = "data_review")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataReview;

    public Review() {
    }

    public Review(Usuario usuario, Game game, Integer nota, String avaliacao, LocalDate dataReview) {
        this.usuario = usuario;
        this.game = game;
        this.nota = nota;
        this.avaliacao = avaliacao;
        this.dataReview = dataReview;
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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        this.nota = nota;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataReview() {
        return dataReview;
    }

    public void setDataReview(LocalDate dataReview) {
        this.dataReview = dataReview;
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
