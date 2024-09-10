package app.vendaServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import app.entity.Cliente;
import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;
import app.service.ClienteService;
import app.service.ProdutoService;
import app.service.VendaService;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class VendaServiceTest {

	@Autowired
	VendaService vendaService;

	@MockBean
	VendaRepository vendaRepository;

	@MockBean
	ProdutoService produtoService;

	@MockBean
	ClienteService clienteService;

	@BeforeEach
	void setup() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setIdade(17);
		cliente.setNome("Cliente Teste");
		cliente.setTelefone("123456789");
		cliente.setCpf("054.854.691-67");
		cliente.setEndereco("Endereço Teste");

		Produto produto = new Produto();
		produto.setId(1L);
		produto.setNome("Banana");
		produto.setDescricao("Descrição do Produto Teste");
		produto.setPreco(600.0);

		Produto produto02 = new Produto();
		produto02.setId(2L);
		produto02.setNome("Produto Teste");
		produto02.setDescricao("Descrição do Produto Teste");
		produto02.setPreco(100.0);

		Venda vendaBanana = new Venda();
		vendaBanana.setCliente(cliente);
		vendaBanana.setDescricao("Observação da Venda");
		vendaBanana.setObservacao("aaaaaaaaa");
		vendaBanana.setProdutos(Arrays.asList(produto));
		vendaBanana.setVlTotal(600.0);

		Mockito.when(vendaRepository.save(Mockito.any())).thenReturn(vendaBanana);

		Mockito.when(produtoService.findById(1L)).thenReturn(produto);

		Mockito.when(produtoService.findById(2L)).thenReturn(produto02);

		Mockito.when(clienteService.findById(1L)).thenReturn(cliente);
	}

	@Test
	@DisplayName("Não deve permitir venda acima de 500 para clientes menores de 18 anos")
	void naoDevePermitirVendaParaMenoresDeIdade() {
		// Criar e salvar cliente
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setIdade(17);
		cliente.setNome("Cliente Teste");
		cliente.setTelefone("123456789");
		cliente.setCpf("123.456.789-00");
		cliente.setEndereco("Endereço Teste");

		// Criar e salvar produto
		Produto produto = new Produto();
		produto.setId(1L);
		produto.setNome("Produto Teste");
		produto.setDescricao("Descrição do Produto Teste");
		produto.setPreco(600.0);

		// Criar venda
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setObservacao("Observação da Venda");
		venda.setProdutos(Arrays.asList(produto));
		venda.setVlTotal(600.0);

		// Espera-se que o método save lance uma RuntimeException
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			vendaService.save(venda);
		});

		// Verificar a mensagem da exceção
		assertEquals("Clientes menores de 18 anos não podem comprar acima de 500 reais.", exception.getMessage());
	}

	@Test
	@DisplayName("Realizar venda corretamente ")
	void salvarVenda() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setIdade(25);

		Produto produto = new Produto();
		produto.setId(2L);
		produto.setNome("Produto Teste");
		produto.setDescricao("Descrição do Produto Teste");
		produto.setPreco(100.0);

		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setDescricao("ssss sjsjs");
		venda.setObservacao("aaaaaaaaa");
		venda.setProdutos(Arrays.asList(produto));
		venda.setVlTotal(100.0);

		assertEquals("Venda cadastrado", vendaService.save(venda));
	}

	@Test
	@DisplayName("Buscar todas as vendas")
	void buscartds() {
	    // Criar clientes
	    Cliente cliente1 = new Cliente();
	    cliente1.setId(1L);
	    cliente1.setIdade(25);
	    
	    Cliente cliente2 = new Cliente();
	    cliente2.setId(2L);
	    cliente2.setIdade(30);

	    // Criar produtos
	    Produto produto1 = new Produto();
	    produto1.setId(1L);
	    produto1.setNome("Produto Teste 1");
	    produto1.setDescricao("Descrição do Produto Teste 1");
	    produto1.setPreco(100.0);

	    Produto produto2 = new Produto();
	    produto2.setId(2L);
	    produto2.setNome("Produto Teste 2");
	    produto2.setDescricao("Descrição do Produto Teste 2");
	    produto2.setPreco(150.0);

	    // Criar vendas
	    Venda venda1 = new Venda();
	    venda1.setCliente(cliente1);
	    venda1.setDescricao("Venda 1");
	    venda1.setObservacao("Observação 1");
	    venda1.setProdutos(Arrays.asList(produto1));
	    venda1.setVlTotal(100.0);

	    Venda venda2 = new Venda();
	    venda2.setCliente(cliente2);
	    venda2.setDescricao("Venda 2");
	    venda2.setObservacao("Observação 2");
	    venda2.setProdutos(Arrays.asList(produto2));
	    venda2.setVlTotal(150.0);

	    // Configurar mocks
	    Mockito.when(vendaRepository.findAll()).thenReturn(Arrays.asList(venda1, venda2));

	    vendaService.save(venda1);
	    vendaService.save(venda2);

	  
	    List<Venda> vendas = vendaService.findAll();

	    assertNotNull(vendas);
	    assertEquals(2, vendas.size());
	    assertTrue(vendas.contains(venda1));
	    assertTrue(vendas.contains(venda2));
	}
	
	@Test
	@DisplayName("Excluir venda inexistente")
	void excluirVendaInexistente() {
	   
	    Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        vendaService.delete(1L);
	    });

	   
	    assertEquals("Venda não encontrada", exception.getMessage());
	}

	
	@Test
	@DisplayName("Tentar atualizar venda inexistente")
	void atualizarVendaInexistente() {
	    Mockito.when(vendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

	    Venda venda = new Venda();
	    venda.setObservacao("Nova observação");

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        vendaService.update(venda, 1L);
	    });

	    assertEquals("Venda não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar as 10 maiores vendas")
	void buscarDezMaioresVendas() {
	    Venda venda1 = new Venda();
	    venda1.setVlTotal(100.0);

	    Venda venda2 = new Venda();
	    venda2.setVlTotal(200.0);

	    Mockito.when(vendaRepository.findTop10ByOrderByVlTotalDesc())
	        .thenReturn(Arrays.asList(venda2, venda1));

	    List<Venda> maioresVendas = vendaService.DezMaioresVendas();

	    assertEquals(2, maioresVendas.size());
	    assertEquals(200.0, maioresVendas.get(0).getVlTotal());
	    assertEquals(100.0, maioresVendas.get(1).getVlTotal());
	}
	@Test
	@DisplayName("Excluir venda existente")
	void excluirVendaExistente() {
	    Venda venda = new Venda();
	    venda.setId(1L);

	    Mockito.when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

	    String result = vendaService.delete(1L);

	    assertEquals("Venda deletada com sucesso!", result);
	    Mockito.verify(vendaRepository, Mockito.times(1)).deleteById(1L);
	}
	@Test
	@DisplayName("Não deve atualizar venda com cliente nulo")
	void naoDeveAtualizarVendaComClienteNulo() {
	    Venda venda = new Venda();
	    venda.setId(1L);
	    venda.setCliente(null);  // Cliente nulo
	    venda.setProdutos(Arrays.asList(new Produto()));  // Lista de produtos válida

	    Mockito.when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

	    RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
	        vendaService.update(venda, 1L);
	    });

	    assertEquals("Cliente da venda é nulo", thrownException.getMessage());
	}

	@Test
	@DisplayName("Não deve atualizar venda com lista de produtos vazia")
	void naoDeveAtualizarVendaComProdutosVazio() {
	    Venda venda = new Venda();
	    venda.setId(1L);
	    venda.setCliente(new Cliente());  // Cliente válido
	    venda.setProdutos(new ArrayList<>());  // Lista de produtos vazia

	    Mockito.when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

	    RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
	        vendaService.update(venda, 1L);
	    });

	    assertEquals("Lista de produtos da venda está vazia", thrownException.getMessage());
	}


	@Test
	@DisplayName("Deve permitir venda para menor de idade com valor abaixo de 500 reais")
	void devePermitirVendaParaMenorDeIdadeComValorAbaixoDe500() {
	    Cliente cliente = new Cliente();
	    cliente.setIdade(16);

	    Produto produto1 = new Produto();
	    produto1.setPreco(100.0);
	    Produto produto2 = new Produto();
	    produto2.setPreco(200.0);

	    Venda venda = new Venda();
	    venda.setCliente(cliente);
	    venda.setProdutos(List.of(produto1, produto2));

	    Mockito.when(vendaRepository.save(Mockito.any(Venda.class))).thenReturn(venda);

	    String resultado = vendaService.save(venda);

	    assertEquals("Venda cadastrado", resultado);
	}


}
