package com.the_gathering_hub.TGH.dto;

import com.the_gathering_hub.TGH.model.Game;

public class GameDTO {

    private Integer id;
    private String nome;
    private String dataLancamento;
    private String developer;
    private String publisher;
    private String descricao;
    private String genero;

    public GameDTO(Game game) {
        this.id = game.getId();
        this.nome = game.getNome();
        this.dataLancamento = game.getDataLancamento().toString();
        this.developer = game.getDeveloper();
        this.publisher = game.getPublisher();
        this.descricao = game.getDescricao();
        this.genero = game.getGenero();
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

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
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
}
