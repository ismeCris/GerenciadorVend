package app.vendaServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import app.entity.Produto;
import app.repository.ProdutoRepositoy;
import app.service.ProdutoService;

@SpringBootTest
public class ProdutoServiceTest {

	@Autowired
	ProdutoService produtoService;

	 @Test
	    @DisplayName("Salvar Produto com sucesso")
	    void salvarProduto() {
	        // Cenário: Criar um novo produto
	        Produto produto = new Produto();
	        produto.setNome("Produto Teste");
	        produto.setDescricao("Descrição do Produto Teste");
	        produto.setPreco(100.0);

	        // Chama o serviço para salvar o produto
	        String resultado = produtoService.save(produto);

	        // Verifica o retorno esperado
	        assertEquals("Produto salvo com sucesso", resultado);
	    }

	    @Test
	    @DisplayName("Buscar Produto por ID com sucesso")
	    void buscarProdutoPorId() {
	        // Inserir um produto no banco antes de buscar
	        Produto produto = new Produto();
	        produto.setNome("Produto Teste");
	        produto.setDescricao("Descrição do Produto Teste");
	        produto.setPreco(100.0);

	        // Salvar o produto para garantir que ele exista
	        produtoService.save(produto);

	        // Agora buscar o produto recém salvo
	        Produto resultado = produtoService.findById(produto.getId()); // Use o ID gerado

	        // Verificar se o produto foi encontrado
	        assertNotNull(resultado);
	        assertEquals("Produto Teste", resultado.getNome());
	    }
	    @Test
	    @DisplayName("Salvar Produto com preço inválido")
	    void precoInvalido() {
	        // Criar um produto com preço inválido
	        Produto produto = new Produto();
	        produto.setNome("Produto com Preço Inválido");
	        produto.setDescricao("Descrição do Produto com Preço Inválido");
	        produto.setPreco(-10.0); // Preço negativo

	        // Verificar se a exceção de validação é lançada
	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            produtoService.save(produto);
	        });

	        assertTrue(exception.getMessage().contains("Preço do produto deve ser maior que zero"));
	    }

}
