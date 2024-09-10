package app.vendaServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.entity.Funcionario;
import app.service.FuncionarioService;

@SpringBootTest
public class FuncionarioServiceTest {

	@Autowired
	FuncionarioService funcionarioService; // Injeta o serviço para ser testado

	
    // Teste para salvar um funcionário com sucesso
    @Test
    @DisplayName("Salvar Funcionario com sucesso")
    void salvarFuncionario() {
        // Cenário: Criar um novo funcionário
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Silva");
        funcionario.setEmail("joao.silva@example.com");
        funcionario.setCpf("054.854.691-67");
        funcionario.setTelefone("(11) 98765-4321");

        // Chama o serviço para salvar o funcionário
        String resultado = funcionarioService.save(funcionario);

        // Verifica o retorno esperado
        assertEquals("Funcionario Cadastrado com sucesso", resultado);
    }

    @Test
    @DisplayName("Buscar Funcionario por ID com sucesso")
    void buscarFuncionarioPorId() {
        // Inserir um funcionário no banco antes de buscar
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Silva");
        funcionario.setEmail("joao.silva@example.com");
        funcionario.setCpf("054.854.691-67");
        funcionario.setTelefone("(11) 98765-4321");

        // Salvar o funcionário para garantir que ele exista
        funcionarioService.save(funcionario);

        // Agora buscar o funcionário recém salvo
        Funcionario resultado = funcionarioService.findById(funcionario.getId()); // Use o ID gerado
        
        // Verificar se o funcionário foi encontrado
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    @DisplayName("Excluir Funcionario com sucesso")
    void excluirFuncionario() {
        // Criar um funcionário
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria Silva");
        funcionario.setEmail("maria.silva@example.com");
        funcionario.setCpf("054.854.691-67");
        funcionario.setTelefone("(11) 98765-4321");

        // Salvar o funcionário no banco
        funcionarioService.save(funcionario);

        // Excluir o funcionário pelo ID gerado
        funcionarioService.delete(funcionario.getId());

        // Verificar se o funcionário foi realmente excluído
        Funcionario funcionarioExcluido = funcionarioService.findById(funcionario.getId());

        // Verifique se o funcionário retornado é null (o que indica que foi excluído)
        assertNull(funcionarioExcluido);
    }

    @Test
    @DisplayName("Salvar Funcionario com CPF inválido")
    void CpfInvalido() {
        // Criar um funcionário
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria Silva");
        funcionario.setEmail("maria.silva@example.com");
        funcionario.setCpf("054.854.691-00");
        funcionario.setTelefone("(11) 98765-4321");

     // Verificar se a exceção de validação é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.save(funcionario);
        });

    }

    @Test
    @DisplayName("Atualizar Funcionario inexistente")
    void atualizarFuncionarioInexistente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Carlos Silva");
        funcionario.setEmail("carlos.silva@example.com");
        funcionario.setCpf("054.854.691-67");
        funcionario.setTelefone("(11) 98765-4321");

        // Tentar atualizar um funcionário que não existe
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.update(funcionario, 999L); // ID inexistente
        });

        assertTrue(exception.getMessage().contains("Funcionário com ID 999 não encontrado"));
    }
    
    @Test
    @DisplayName("Buscar todos os Funcionarios")
    void buscarTodosOsFuncionarios() {
    	Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Ana Souza");
        funcionario1.setEmail("ana.souza@example.com");
        funcionario1.setCpf("054.854.691-67");
        funcionario1.setTelefone("(11) 98765-4321");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setNome("Lucas Oliveira");
        funcionario2.setEmail("lucas.oliveira@example.com");
        funcionario2.setCpf("054.854.691-67");
        funcionario2.setTelefone("(11) 98765-4322");

        funcionarioService.save(funcionario1);
        funcionarioService.save(funcionario2);

        List<Funcionario> funcionarios = funcionarioService.findAll();

        assertNotNull(funcionarios);
        assertTrue(funcionarios.size() >= 2); // Verifica que pelo menos 2 funcionários foram salvos
    }
    @Test
    @DisplayName("Excluir Funcionario inexistente")
    void excluirFuncionarioInexistente() {
        // Tentar excluir um funcionário com ID que não existe
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.delete(999L); // ID inexistente
        });

        assertTrue(exception.getMessage().contains("Funcionário não encontrado!"));
    }
    @Test
    @DisplayName("Salvar Funcionario sem nome")
    void salvarFuncionarioSemNome() {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail("maria.silva@example.com");
        funcionario.setCpf("054.854.691-67");
        funcionario.setTelefone("(11) 98765-4321");

        // Verificar se a exceção é lançada devido à ausência do nome
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.save(funcionario);
        });

        assertTrue(exception.getMessage().contains("Nome do funcionário não pode ser nulo ou vazio"));
    }


}
