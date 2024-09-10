package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoService produtoService;

	public String save(Venda venda) {
		double valorTotal = this.calcularTotal(venda.getProdutos());
		venda.setVlTotal(valorTotal);
		if (venda.getCliente().getIdade() < 18 && venda.getVlTotal() > 500.0) {
			throw new RuntimeException("Clientes menores de 18 anos não podem comprar acima de 500 reais.");
		}

		this.vendaRepository.save(venda);
		return "Venda cadastrado";
	}

	public String update(Venda venda, Long id) {
	    Venda vendaExistente = vendaRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

	    if (venda.getCliente() == null) {
	        throw new RuntimeException("Cliente da venda é nulo");
	    }

	    double valorTotal = this.calcularTotal(venda.getProdutos());
	    
	    if (venda.getCliente().getIdade() < 18 && valorTotal > 500.0) {
	        throw new RuntimeException("Clientes menores de 18 anos não podem comprar acima de 500 reais.");
	    }
	    
	    if (venda.getProdutos() == null || venda.getProdutos().isEmpty()) {
	        throw new RuntimeException("Lista de produtos da venda está vazia");
	    }

	    vendaExistente.setProdutos(venda.getProdutos());
	    vendaExistente.setVlTotal(valorTotal);
	    vendaExistente.setDescricao(venda.getDescricao());
	    vendaExistente.setObservacao(venda.getObservacao());
	    
	    vendaRepository.save(vendaExistente);
	    return "Venda atualizada com sucesso!";
	}




	public Venda findById(Long id) {
		Optional<Venda> optional = this.vendaRepository.findById(id);
		return optional.orElse(null);
	}

	public List<Venda> findAll() {
		return this.vendaRepository.findAll();
	}
	public String delete(Long id) {
	    // Verifica se a venda existe
	    Venda venda = this.vendaRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

	    // Deleta a venda
	    this.vendaRepository.deleteById(id);
	    return "Venda deletada com sucesso!";
	}


	public double calcularTotal(List<Produto> produtos) {
		if (produtos == null) {
			produtos = new ArrayList<>(); // Inicializa com uma lista vazia se produtos for nulo
		}

		double valorTotal = 0;
		for (Produto produto : produtos) {
			if (produto != null) { // Verifica se o produto não é nulo
				Produto produtoEncontrado = produtoService.findById(produto.getId()); // Use o mock para obter o produto
				if (produtoEncontrado != null) {
					valorTotal += produtoEncontrado.getPreco();
				}
			}
		}
		return valorTotal;
	}

	/*-----------------------------------------------------------------------*/
	public List<Venda> VendasPorNomeDeFuncionario(String nome) {
		return this.vendaRepository.VendasPorNomeDeFuncionario(nome);
	}

	public List<Venda> VendasPorNomeDeCliente(String nome) {
		return this.vendaRepository.VendasPorNomeDeCliente(nome);
	}

	public List<Venda> DezMaioresVendas() {
		return this.vendaRepository.findTop10ByOrderByVlTotalDesc();
	}

}
