package com.the_gathering_hub.TGH.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 55)
    private String nome;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    @JsonIgnore
    private String senha;

    @Column(name = "qtd_jogos_jogados")
    private Integer qtdJogosJogados = 0;

    @Column(name = "qtd_reviews_feitas")
    private Integer qtdReviewsFeitas = 0;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JogoJogado> jogosJogados = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getQtdJogosJogados() {
        return qtdJogosJogados;
    }

    public void setQtdJogosJogados(Integer qtdJogosJogados) {
        this.qtdJogosJogados = qtdJogosJogados;
    }

    public Integer getQtdReviewsFeitas() {
        return qtdReviewsFeitas;
    }

    public void setQtdReviewsFeitas(Integer qtdReviewsFeitas) {
        this.qtdReviewsFeitas = qtdReviewsFeitas;
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
