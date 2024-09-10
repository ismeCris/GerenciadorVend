package app.vendaControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.controller.ClienteController;
import app.entity.Cliente;
import app.service.ClienteService;

public class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    ClienteService clienteService;

    @InjectMocks
    ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testGetClienteById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Cliente Teste"));

        verify(clienteService, times(1)).findById(1L);
    }

    @Test
    void testCreateCliente() throws Exception {
        String clienteJson = "{\"nome\":\"Cliente Teste\"}";

        // Configure o mock para retornar um resultado esperado
        when(clienteService.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso!");

        mockMvc.perform(post("/cliente/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente salvo com sucesso!"));

        verify(clienteService, times(1)).save(any(Cliente.class));
    }
    @Test
    void testGetAllClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 1");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente 2");

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/cliente/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Cliente 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Cliente 2"));

        verify(clienteService, times(1)).findAll();
    }

    @Test
    void testGetClienteByIdNotFound() throws Exception {
        // Simula o serviço retornando null para o cliente
        when(clienteService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/cliente/findById/1"))
                .andExpect(status().isNotFound()); // Verifica se o status é 404
    }


    @Test
    void testUpdateCliente() throws Exception {
        String clienteJson = "{\"nome\":\"Cliente Atualizado\"}";

        // Configura o mock para retornar uma mensagem de sucesso
        when(clienteService.update(any(Cliente.class), any(Long.class))).thenReturn("Cliente atualizado com sucesso!");

        mockMvc.perform(put("/cliente/update/1") // Certifique-se de usar PUT aqui
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isOk()) // Verifica se o status é 200
                .andExpect(content().string("Cliente atualizado com sucesso!"));

        verify(clienteService, times(1)).update(any(Cliente.class), any(Long.class));
    }

    @Test
    void testDeleteCliente() throws Exception {
        // Configure o mock para retornar um resultado esperado
        when(clienteService.delete(1L)).thenReturn("Cliente deletado com sucesso!");

        mockMvc.perform(delete("/cliente/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente deletado com sucesso!"));

        verify(clienteService, times(1)).delete(1L);
    }

    @Test
    void testGetClienteResponseFormat() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").isNotEmpty());

        verify(clienteService, times(1)).findById(1L);
    }
    
    @Test
    void testGetClientesIdadeEntre18e35() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 18-35");
        cliente1.setIdade(25);

        List<Cliente> clientes = Collections.singletonList(cliente1);

        when(clienteService.findIdade18a35()).thenReturn(clientes);

        mockMvc.perform(get("/cliente/idadeEntre18e35"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Cliente 18-35"))
                .andExpect(jsonPath("$[0].idade").value(25));

        verify(clienteService, times(1)).findIdade18a35();
    }


}
