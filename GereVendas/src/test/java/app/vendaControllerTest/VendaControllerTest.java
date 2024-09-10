package app.vendaControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import app.controller.VendaController;
import app.entity.Venda;
import app.service.VendaService;

public class VendaControllerTest {

	private MockMvc mockMvc;

    @Mock
    VendaService vendaService;

    @InjectMocks
    VendaController vendaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendaController).build();
    }
/*
    @Test
    void testGetVendaById() throws Exception {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setVlTotal(100.0);

        when(vendaService.findById(1L)).thenReturn(venda);

        mockMvc.perform(get("/venda/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.valorTotal").value(100.0));

        verify(vendaService, times(1)).findById(1L);
    }

*/
    @Test
    void testCreateVenda() throws Exception {
        String vendaJson = "{\"valorTotal\":100.0}";

        when(vendaService.save(any(Venda.class))).thenReturn("Venda salva com sucesso!");

        mockMvc.perform(post("/venda/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Venda salva com sucesso!"));

        verify(vendaService, times(1)).save(any(Venda.class));
    }

    @Test
    void testUpdateVenda() throws Exception {
        String vendaJson = "{\"valorTotal\":150.0}";

        when(vendaService.update(any(Venda.class), any(Long.class))).thenReturn("Venda atualizada com sucesso!");

        mockMvc.perform(put("/venda/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Venda atualizada com sucesso!"));

        verify(vendaService, times(1)).update(any(Venda.class), any(Long.class));
    }

    @Test
    void testDeleteVenda() throws Exception {
        when(vendaService.delete(1L)).thenReturn("Venda deletada com sucesso!");

        mockMvc.perform(delete("/venda/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Venda deletada com sucesso!"));

        verify(vendaService, times(1)).delete(1L);
    }
    @Test
    void testCreateVendaWithException() throws Exception {
        String vendaJson = "{\"valorTotal\":100.0}";

        when(vendaService.save(any(Venda.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        mockMvc.perform(post("/venda/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deu Erro! Erro ao salvar"));

        verify(vendaService, times(1)).save(any(Venda.class));
    }
    @Test
    void testUpdateVendaWithException() throws Exception {
        String vendaJson = "{\"valorTotal\":150.0}";

        when(vendaService.update(any(Venda.class), any(Long.class))).thenThrow(new RuntimeException("Erro ao atualizar"));

        mockMvc.perform(put("/venda/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deu erro! Erro ao atualizar"));

        verify(vendaService, times(1)).update(any(Venda.class), any(Long.class));
    }
    @Test
    void testFindByIdVendaNotFound() throws Exception {
        when(vendaService.findById(1L)).thenThrow(new RuntimeException("Venda não encontrada"));

        mockMvc.perform(get("/venda/findById/1"))
                .andExpect(status().isBadRequest());

        verify(vendaService, times(1)).findById(1L);
    }
    @Test
    void testSaveVendaWithInvalidCliente() throws Exception {
        String vendaJson = "{\"valorTotal\":600.0, \"cliente\": {\"idade\": 16}}";

        when(vendaService.save(any(Venda.class))).thenThrow(new RuntimeException("Clientes menores de 18 anos não podem comprar acima de 500 reais."));

        mockMvc.perform(post("/venda/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deu Erro! Clientes menores de 18 anos não podem comprar acima de 500 reais."));

        verify(vendaService, times(1)).save(any(Venda.class));
    }
    /*
    @Test
    void testFindAllVendas() throws Exception {
        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setVlTotal(100.0);

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setVlTotal(200.0);

        List<Venda> lista = Arrays.asList(venda1, venda2);

        when(vendaService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/venda/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].valorTotal").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].valorTotal").value(200.0));

        verify(vendaService, times(1)).findAll();
    }
*/

/*
    @Test
    void testFindByClienteNomeContains() throws Exception {
        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setCliente("Cliente Teste 1");

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setCliente("Cliente Teste 2");

        List<Venda> lista = Arrays.asList(venda1, venda2);

        when(vendaService.VendasPorNomeDeCliente("Cliente")).thenReturn(lista);

        mockMvc.perform(get("/venda/findByClienteNomeContains")
                .param("nome", "Cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].clienteNome").value("Cliente Teste 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].clienteNome").value("Cliente Teste 2"));

        verify(vendaService, times(1)).VendasPorNomeDeCliente("Cliente");
    }

    @Test
    void testFindByFuncionarioNomeContains() throws Exception {
        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setFuncionarioNome("Funcionario Teste 1");

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setFuncionarioNome("Funcionario Teste 2");

        List<Venda> lista = Arrays.asList(venda1, venda2);

        when(vendaService.VendasPorNomeDeFuncionario("Funcionario")).thenReturn(lista);

        mockMvc.perform(get("/venda/findByFuncionarioNomeContains")
                .param("nome", "Funcionario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].funcionarioNome").value("Funcionario Teste 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].funcionarioNome").value("Funcionario Teste 2"));

        verify(vendaService, times(1)).VendasPorNomeDeFuncionario("Funcionario");
    }*/
/*
    @Test
    void testFindTop10ByOrderByValorTotalDesc() throws Exception {
        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setVlTotal(300.0);

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setVlTotal(250.0);

        List<Venda> lista = Arrays.asList(venda1, venda2);

        when(vendaService.DezMaioresVendas()).thenReturn(lista);

        mockMvc.perform(get("/venda/findTop10ByOrderByValorTotalDesc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].valorTotal").value(300.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].valorTotal").value(250.0));

        verify(vendaService, times(1)).DezMaioresVendas();
    }*/
 
}
