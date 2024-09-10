package app.vendaServiceTest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.entity.Cliente;
import app.service.ClienteService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest
public class ClienteServiceTest {

	@Autowired
	ClienteService clienteService;// Injeta o serviço para ser testado

	@Test
	@DisplayName("Salvar Cliente com sucesso")
	void salvarCliente() {
		// Cenário: Criar um novo cliente
		Cliente cliente = new Cliente();
		cliente.setNome("João Silva");
		cliente.setEmail("joao.silva@example.com");
		cliente.setCpf("054.854.691-67");
		cliente.setTelefone("(11) 98765-4321");
		cliente.setEndereco("Rua Exemplo, 123"); // Adicione o endereço

		// Chama o serviço para salvar o cliente
		String resultado = clienteService.save(cliente);

		// Verifica o retorno esperado
		assertEquals("Cliente salvo com sucesso", resultado);
	}

	@Test
	@DisplayName("Buscar Cliente por ID com sucesso")
	void buscarClientePorId() {
		// Inserir um Cliente no banco antes de buscar
		Cliente cliente = new Cliente();
		cliente.setNome("João Silva");
		cliente.setEmail("joao.silva@example.com");
		cliente.setCpf("054.854.691-67");
		cliente.setTelefone("(11) 98765-4321");
		cliente.setEndereco("Rua Exemplo, 123"); // Adicione o endereço

		// Salvar o Cliente para garantir que ele exista
		clienteService.save(cliente);

		// Agora buscar o Cliente recém salvo
		Cliente resultado = clienteService.findById(cliente.getId()); // Use o ID gerado

		// Verificar se o Cliente foi encontrado
		assertNotNull(resultado);
		assertEquals("João Silva", resultado.getNome());
	}

	@Test
	@DisplayName("Excluir Cliente com sucesso")
	void excluirFuncionario() {
		// Criar um funcionário
		Cliente cliente = new Cliente();
		cliente.setNome("Maria Silva");
		cliente.setEmail("maria.silva@example.com");
		cliente.setCpf("054.854.691-67");
		cliente.setTelefone("(11) 98765-4321");
		cliente.setEndereco("Rua Exemplo, 123");

		// Salvar o Cliente no banco
		clienteService.save(cliente);

		// Excluir o Cliente pelo ID gerado
		clienteService.delete(cliente.getId());

		// Verificar se o Cliente foi realmente excluído
		Cliente clienteExcluido = clienteService.findById(cliente.getId());

		// Verifique se o Cliente retornado é null (o que indica que foi excluído)
		assertNull(clienteExcluido);
	}

	@Test
	@DisplayName("Salvar Cliente com CPF inválido")
	void CpfInvalido() {
		// Criar um funcionário
		Cliente cliente = new Cliente();
		cliente.setNome("Maria Silva");
		cliente.setEmail("maria.silva@example.com");
		cliente.setCpf("054.854.691-00");
		cliente.setTelefone("(11) 98765-4321");
		cliente.setEndereco("Rua Exemplo, 123");

		// Verificar se a exceção de validação é lançada
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			clienteService.save(cliente);
		});

	}

	@Test
	@DisplayName("Atualizar Cliente inexistente")
	void atualizarClienteInexistente() {
		Cliente cliente = new Cliente();
		cliente.setId(999); // ID inexistente
		cliente.setNome("Maria Silva");
		cliente.setEmail("maria.silva@example.com");
		cliente.setCpf("054.854.691-00");
		cliente.setTelefone("(11) 98765-4321");
		cliente.setEndereco("Rua Exemplo, 123");

		// Tentar atualizar um Cliente que não existe
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			clienteService.update(cliente, 999L); // ID inexistente
		});

		// Verificar se a mensagem da exceção contém a mensagem esperada
		assertTrue(exception.getMessage().contains("Cliente com ID 999 não encontrado"),
				"A mensagem da exceção não contém o texto esperado.");
	}

	@Test
	@DisplayName("Buscar todos os Cliente")
	void buscarTodosOsCliente() {
		Cliente cliente01 = new Cliente();
		cliente01.setNome("João Silva");
		cliente01.setEmail("joao.silva@example.com");
		cliente01.setCpf("054.854.691-67");
		cliente01.setTelefone("(11) 98765-4321");
		cliente01.setEndereco("Rua Exemplo, 123");

		Cliente cliente02 = new Cliente();
		cliente02.setNome("Lucas Oliveira");
		cliente02.setEmail("lucas.oliveira@example.com");
		cliente02.setCpf("382.383.898-95");
		cliente02.setTelefone("(11) 98765-4322");
		cliente02.setEndereco("Rua Outro Exemplo, 456");

		clienteService.save(cliente01);
		clienteService.save(cliente02);

		List<Cliente> clientes = clienteService.findAll();

		assertNotNull(clientes);
		assertTrue(clientes.size() >= 2); // Verifica que pelo menos 2 clientes foram salvos
	}

	@Test
	@DisplayName("Excluir Cliente inexistente")
	void excluirClienteInexistente() {
		// Tentar excluir um funcionário com ID que não existe
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			clienteService.delete(999L); // ID inexistente
		});

		assertTrue(exception.getMessage().contains("Cliente não encontrado!"));
	}

	@Test
	@DisplayName("Salvar Cliente sem nome")
	void salvarClienteSemNome() {
	    Cliente cliente = new Cliente();
	    cliente.setNome(null); // Nome não pode ser nulo
	    cliente.setEmail("teste@example.com");
	    cliente.setCpf("123.456.789-00");
	    cliente.setTelefone("(11) 98765-4321");
	    cliente.setEndereco("Rua Exemplo, 123");

	    assertThrows(RuntimeException.class, () -> {
	        clienteService.save(cliente);
	    });
	}


}
