package com.example.demo.controladores;

import com.example.demo.modelos.Produto;
import com.example.demo.servicos.ProdutoServico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoControlador {

    private final ProdutoServico produtoServico;

    public ProdutoControlador(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    @GetMapping("/estoque-critico/{quantidade}")
    public ResponseEntity<List<Produto>> buscarEstoquesCriticos(@PathVariable int quantidade) {
        List<Produto> produtos = produtoServico.buscarEstoquesCriticos(quantidade);
        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto salvo = produtoServico.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}

