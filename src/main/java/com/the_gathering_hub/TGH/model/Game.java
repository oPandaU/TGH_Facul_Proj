package com.the_gathering_hub.TGH.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 55)
    private String nome;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDate dataLancamento;

    @Column(length = 50)
    private String developer;

    @Column(length = 50)
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 20)
    private String genero;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<JogoJogado> jogosJogados = new ArrayList<>();


    public Game() {
    }

    public Game(String nome, LocalDate dataLancamento, String developer, String publisher, String descricao, String genero) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.developer = developer;
        this.publisher = publisher;
        this.descricao = descricao;
        this.genero = genero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<JogoJogado> getJogosJogados() {
        return jogosJogados;
    }

    public void setJogosJogados(List<JogoJogado> jogosJogados) {
        this.jogosJogados = jogosJogados;
    }
}
