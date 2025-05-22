package model;


public class Review {

    private String jogoNome;
    private String texto;

    public Review(String jogoNome, String texto) {
        this.jogoNome = jogoNome;
        this.texto = texto;
    }

    public String getJogoNome() {
        return jogoNome;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return "Jogo: " + jogoNome + "\nReview: " + texto + "\n--------------------";
    }
}
