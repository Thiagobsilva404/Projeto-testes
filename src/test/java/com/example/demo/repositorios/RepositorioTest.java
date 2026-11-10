package com.example.demo.repositorios;

import com.example.demo.modelos.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import  static  org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
//não deixem eu esquecer
public class RepositorioTest {
    @Autowired
    ProdutoRepositorio produtoRepositorio;
    @Test
    public void deveSalvarProduto(){
        Produto produto = new Produto();
        produto.setNome("Arroz");
        produto.setDataDeValidade(
                LocalDate.of(2026,12,25));
        produto.setQuantidadeEmEstoque(5);
        produtoRepositorio.save(produto);
        assertNotEquals(0,produto.getId());
    }
    @Test
    public void deveRetornarProdutoCritico(){
        Produto produto1 = new Produto();
        produto1.setNome("Arroz");
        produto1.setDataDeValidade(
                LocalDate.of(2026,12,25));
        produto1.setQuantidadeEmEstoque(50);
        produtoRepositorio.save(produto1);
        Produto produto2 = new Produto();
        produto2.setNome("Feijão");
        produto2.setDataDeValidade(
                LocalDate.of(2026,12,25));
        produto2.setQuantidadeEmEstoque(10);
        produtoRepositorio.save(produto2);
        List<Produto> produtosCriticos =
            produtoRepositorio.buscarProdutosComEstoqueCritico(20);

        assertEquals(1, produtosCriticos.size());
        assertEquals("Feijão",
                produtosCriticos.get(0).getNome());
       List<Produto> produtos =
               produtoRepositorio.findAll();
       System.out.println(produtos.size());
    }
}
