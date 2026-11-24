package com.example.demo.servicos;

import com.example.demo.modelos.Produto;
import com.example.demo.repositorios.ProdutoRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServicoTest {

    @Mock
    private ProdutoRepositorio produtoRepositorio;

    @InjectMocks
    private ProdutoServico produtoServico;

    @Test
    void deveBuscarProdutosComEstoqueCritico() {
        Produto produto = criarProduto("Arroz", 5);
        when(produtoRepositorio.buscarProdutosComEstoqueCritico(10))
                .thenReturn(List.of(produto));

        List<Produto> resultado = produtoServico.buscarEstoquesCriticos(10);

        assertEquals(1, resultado.size());
        assertSame(produto, resultado.get(0));
        verify(produtoRepositorio).buscarProdutosComEstoqueCritico(10);
    }

    @Test
    void deveFalharQuandoNaoExistirProdutoComEstoqueCritico() {
        when(produtoRepositorio.buscarProdutosComEstoqueCritico(10))
                .thenReturn(List.of());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> produtoServico.buscarEstoquesCriticos(10)
        );

        assertEquals("Nenhum produto em estoque crítico", exception.getMessage());
        verify(produtoRepositorio).buscarProdutosComEstoqueCritico(10);
    }

    @Test
    void deveSalvarProdutoComQuantidadeValida() {
        Produto produto = criarProduto("Feijão", 15);
        when(produtoRepositorio.save(produto)).thenReturn(produto);

        Produto resultado = produtoServico.salvar(produto);

        assertSame(produto, resultado);
        verify(produtoRepositorio).save(produto);
    }

    @Test
    void naoDeveSalvarProdutoComQuantidadeNegativa() {
        Produto produto = criarProduto("Macarrão", -1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> produtoServico.salvar(produto)
        );

        assertEquals("Quantidade não pode ser negativa", exception.getMessage());
        verifyNoInteractions(produtoRepositorio);
    }

    private Produto criarProduto(String nome, int quantidade) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidadeEmEstoque(quantidade);
        return produto;
    }
}
