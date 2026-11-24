package com.example.demo.controladores;

import com.example.demo.modelos.Produto;
import com.example.demo.repositorios.ProdutoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @BeforeEach
    void limparBanco() {
        produtoRepositorio.deleteAll();
    }

    @Test
    void deveBuscarEstoquesCriticos() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Arroz");
        produto.setQuantidadeEmEstoque(5);
        produto.setDataDeValidade(LocalDate.of(2026, 12, 25));
        produtoRepositorio.save(produto);

        mockMvc.perform(get("/produtos/estoque-critico/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Arroz"))
                .andExpect(jsonPath("$[0].quantidadeEmEstoque").value(5));
    }

    @Test
    void deveSalvarProduto() throws Exception {
        mockMvc.perform(post("/produtos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Feijão",
                                  "quantidadeEmEstoque": 20,
                                  "dataDeValidade": "2026-12-25"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Feijão"))
                .andExpect(jsonPath("$.quantidadeEmEstoque").value(20));
    }

    @Test
    void deveRetornarErroAoSalvarQuantidadeNegativa() throws Exception {
        mockMvc.perform(post("/produtos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Macarrão",
                                  "quantidadeEmEstoque": -1,
                                  "dataDeValidade": "2026-12-25"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Quantidade não pode ser negativa"));
    }
}
