package app.vendaControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.controller.ProdutoController;
import app.entity.Produto;
import app.service.ProdutoService;

public class ProdutoCrontrollerTest {

    private MockMvc mockMvc;

    @Mock
    ProdutoService produtoService;

    @InjectMocks
    ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void testGetProdutoById() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoService.findById(1L)).thenReturn(produto);

        mockMvc.perform(get("/produto/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Produto Teste"));

        verify(produtoService, times(1)).findById(1L);
    }

    @Test
    void testCreateProduto() throws Exception {
        String produtoJson = "{\"nome\":\"Produto Teste\"}";

        when(produtoService.save(any(Produto.class))).thenReturn("Produto salvo com sucesso!");

        mockMvc.perform(post("/produto/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto salvo com sucesso!"));

        verify(produtoService, times(1)).save(any(Produto.class));
    }

    @Test
    void testUpdateProduto() throws Exception {
        String produtoJson = "{\"nome\":\"Produto Atualizado\"}";

        when(produtoService.update(any(Produto.class), any(Long.class))).thenReturn("Produto atualizado com sucesso!");

        mockMvc.perform(put("/produto/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto atualizado com sucesso!"));

        verify(produtoService, times(1)).update(any(Produto.class), any(Long.class));
    }

    @Test
    void testDeleteProduto() throws Exception {
        when(produtoService.delete(1L)).thenReturn("Produto deletado com sucesso!");

        mockMvc.perform(delete("/produto/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto deletado com sucesso!"));

        verify(produtoService, times(1)).delete(1L);
    }

    @Test
    void testFindAllProdutos() throws Exception {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto Teste 1");

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto Teste 2");

        List<Produto> lista = Arrays.asList(produto1, produto2);

        when(produtoService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/produto/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Produto Teste 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Produto Teste 2"));

        verify(produtoService, times(1)).findAll();
    }
}
