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
import java.util.Collections;
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
    @Test
    void testUpdateVendaWithInvalidData() throws Exception {
        String vendaJson = "{\"valorTotal\":150.0}"; 

        when(vendaService.update(any(Venda.class), any(Long.class)))
                .thenThrow(new RuntimeException("Dados inválidos para atualização"));

        mockMvc.perform(put("/venda/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deu erro! Dados inválidos para atualização"));

        verify(vendaService, times(1)).update(any(Venda.class), any(Long.class));
    }
    @Test
    void testCreateVendaWithMissingFields() throws Exception {
        String vendaJson = "{\"valorTotal\":}"; 

        mockMvc.perform(post("/venda/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isBadRequest()); // Espera um erro de bad request

        verify(vendaService, times(0)).save(any(Venda.class)); // Verifica que o serviço não foi chamado
    }

    @Test
    void testFindAllVendasEmptyList() throws Exception {
        when(vendaService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/venda/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testCreateVendaWithMultipleProducts() throws Exception {
        String vendaJson = "{\"valorTotal\":300.0, \"produtos\":[{\"id\":1, \"preco\":100.0}, {\"id\":2, \"preco\":200.0}]}";

        when(vendaService.save(any(Venda.class))).thenReturn("Venda salva com sucesso!");

        mockMvc.perform(post("/venda/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendaJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Venda salva com sucesso!"));

        verify(vendaService, times(1)).save(any(Venda.class));
    }

}
