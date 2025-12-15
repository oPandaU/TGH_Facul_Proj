package com.the_gathering_hub.TGH.dto;

public class ReviewDTO {

    private Integer usuarioId;
    private Integer gameId;
    private Integer nota;
    private String avaliacao;
    private String dataReview;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDataReview() {
        return dataReview;
    }

    public void setDataReview(String dataReview) {
        this.dataReview = dataReview;
    }
}
