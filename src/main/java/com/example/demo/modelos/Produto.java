package com.example.demo.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nome;
    private int quantidadeEmEstoque;
    private LocalDate dataDeValidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public LocalDate  getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate  dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }


}
