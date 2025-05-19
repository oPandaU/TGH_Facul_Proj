
public class Jogo {

    private String nome;
    private String descricao;
    private String dataLancamento;
    private String desenvolvedora;
    private String publicadora;

    public Jogo(String nome, String descricao, String dataLancamento, String desenvolvedora, String publicadora) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.desenvolvedora = desenvolvedora;
        this.publicadora = publicadora;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public String getPublicadora() {
        return publicadora;
    }
}
