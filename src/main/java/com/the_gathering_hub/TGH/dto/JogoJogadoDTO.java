package com.the_gathering_hub.TGH.dto;

public class JogoJogadoDTO {

    private Integer usuarioId;
    private Integer gameId;
    private String dataJogada;

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

    public String getDataJogada() {
        return dataJogada;
    }

    public void setDataJogada(String dataJogada) {
        this.dataJogada = dataJogada;
    }
}
