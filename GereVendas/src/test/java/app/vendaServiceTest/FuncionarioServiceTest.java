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

	@Test
	@DisplayName("Salvar Funcionario com sucesso")
	void salvarFuncionario() {

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("João Silva");
		funcionario.setEmail("joao.silva@example.com");
		funcionario.setCpf("054.854.691-67");
		funcionario.setTelefone("(11) 98765-4321");

		String resultado = funcionarioService.save(funcionario);

		assertEquals("Funcionario Cadastrado com sucesso", resultado);
	}

	@Test
	@DisplayName("Buscar Funcionario por ID com sucesso")
	void buscarFuncionarioPorId() {

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("João Silva");
		funcionario.setEmail("joao.silva@example.com");
		funcionario.setCpf("054.854.691-67");
		funcionario.setTelefone("(11) 98765-4321");

		funcionarioService.save(funcionario);

		Funcionario resultado = funcionarioService.findById(funcionario.getId());

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

		funcionarioService.save(funcionario);

		funcionarioService.delete(funcionario.getId());

		Funcionario funcionarioExcluido = funcionarioService.findById(funcionario.getId());

		assertNull(funcionarioExcluido);
	}

	@Test
	@DisplayName("Salvar Funcionario com CPF inválido")
	void CpfInvalido() {

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria Silva");
		funcionario.setEmail("maria.silva@example.com");
		funcionario.setCpf("054.854.691-00");
		funcionario.setTelefone("(11) 98765-4321");

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

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			funcionarioService.update(funcionario, 999L);
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
		assertTrue(funcionarios.size() >= 2);
	}

	@Test
	@DisplayName("Excluir Funcionario inexistente")
	void excluirFuncionarioInexistente() {

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			funcionarioService.delete(999L);
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

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			funcionarioService.save(funcionario);
		});

		assertTrue(exception.getMessage().contains("Nome do funcionário não pode ser nulo ou vazio"));
	}

}
