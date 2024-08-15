package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	public List<Venda> VendasPorNomeDeCliente(String nome);

	public List<Venda> VendasPorNomeDeFuncionario(String nome);

	public List<Venda> DezMaioresVendas();
	

}
