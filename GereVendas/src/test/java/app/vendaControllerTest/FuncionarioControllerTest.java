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

import app.controller.FuncionarioController;
import app.entity.Funcionario;
import app.service.FuncionarioService;

public class FuncionarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    FuncionarioService funcionarioService;

    @InjectMocks
    FuncionarioController funcionarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(funcionarioController).build();
    }

    @Test
    void testGetFuncionarioById() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Funcionario Teste");

        when(funcionarioService.findById(1L)).thenReturn(funcionario);

        mockMvc.perform(get("/funcionario/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Funcionario Teste"));

        verify(funcionarioService, times(1)).findById(1L);
    }

    @Test
    void testCreateFuncionario() throws Exception {
        String funcionarioJson = "{\"nome\":\"Funcionario Teste\"}";

        when(funcionarioService.save(any(Funcionario.class))).thenReturn("Funcionario salvo com sucesso!");

        mockMvc.perform(post("/funcionario/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(funcionarioJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Funcionario salvo com sucesso!"));

        verify(funcionarioService, times(1)).save(any(Funcionario.class));
    }

    @Test
    void testUpdateFuncionario() throws Exception {
        String funcionarioJson = "{\"nome\":\"Funcionario Atualizado\"}";

        when(funcionarioService.update(any(Funcionario.class), any(Long.class))).thenReturn("Funcionario atualizado com sucesso!");

        mockMvc.perform(put("/funcionario/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(funcionarioJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Funcionario atualizado com sucesso!"));

        verify(funcionarioService, times(1)).update(any(Funcionario.class), any(Long.class));
    }

    @Test
    void testDeleteFuncionario() throws Exception {
        when(funcionarioService.delete(1L)).thenReturn("Funcionario deletado com sucesso!");

        mockMvc.perform(delete("/funcionario/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Funcionario deletado com sucesso!"));

        verify(funcionarioService, times(1)).delete(1L);
    }

    @Test
    void testFindAllFuncionarios() throws Exception {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        funcionario1.setNome("Funcionario Teste 1");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2L);
        funcionario2.setNome("Funcionario Teste 2");

        List<Funcionario> lista = Arrays.asList(funcionario1, funcionario2);

        when(funcionarioService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/funcionario/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Funcionario Teste 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Funcionario Teste 2"));

        verify(funcionarioService, times(1)).findAll();
    }
}
