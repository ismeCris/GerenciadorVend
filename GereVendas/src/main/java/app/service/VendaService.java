package app.service;

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
    	 if (venda.getCliente().getIdade() < 18 && valorTotal > 500.0) {
    	        throw new RuntimeException("Clientes menores de 18 anos não podem comprar acima de 500 reais.");
    	    }

		venda.setVlTotal(valorTotal);
		this.vendaRepository.save(venda);
		return "Venda cadastrado";
    }


    public String update(Venda venda, Long id) {
        venda.setId(id);
        double valorTotal = this.calcularTotal(venda.getProdutos());
        if (venda.getCliente().getIdade() < 18 && valorTotal > 500.0) {
            throw new RuntimeException("não pode comprar acima de 500 reais");
        }
        venda.setVlTotal(valorTotal);
        this.vendaRepository.save(venda);

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
        this.vendaRepository.deleteById(id);
        return "Venda deletada com sucesso!";
    }
    
   	public double calcularTotal(List<Produto> produtos) {

		double valorTotal = 0;

		for (Produto pt : produtos) {
			Produto produto = this.produtoService.findById(pt.getId());
			valorTotal += produto.getPreco();
		}
		return valorTotal;

	}
   	/*-----------------------------------------------------------------------*/
	public List<Venda> VendasPorNomeDeFuncionario(String nome){
		return this.vendaRepository.VendasPorNomeDeFuncionario(nome);
	}
	
	public List<Venda> VendasPorNomeDeCliente(String nome){
		return this.vendaRepository.VendasPorNomeDeCliente(nome);
	}
	
	 public List<Venda> DezMaioresVendas() {
		return this.vendaRepository.findTop10ByOrderByVlTotalDesc();
	}
	


}
