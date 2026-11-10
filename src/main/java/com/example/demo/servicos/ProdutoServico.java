package com.example.demo.servicos;

import com.example.demo.modelos.Produto;
import com.example.demo.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    //Discutir com eles
    /*private final ProdutoRepositorio produtoRepositorio;

    public ProdutoServico(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }
    */
    public List<Produto> buscarEstoquesCriticos(int limite) {
        List<Produto> produtos = produtoRepositorio.buscarProdutosComEstoqueCritico(limite);

        if (produtos.isEmpty()) {
            throw new RuntimeException("Nenhum produto em estoque crítico");
        }

        return produtos;
    }

    public Produto salvar(Produto produto) {
        if (produto.getQuantidadeEmEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }

        return produtoRepositorio.save(produto);
    }
}
