package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	   // Listar vendas filtradas por parte do nome do cliente
    @Query("SELECT v FROM Venda v WHERE v.cliente.nome LIKE %:nome%")
    List<Venda> VendasPorNomeDeCliente(@Param("nome") String nome);
    
    // Listar vendas filtradas por parte do nome do funcionário
    @Query("SELECT v FROM Venda v WHERE v.funcionario.nome LIKE %:nome%")
    List<Venda> VendasPorNomeDeFuncionario(@Param("nome") String nome);
    
    /*/ Listar as 10 vendas com totais mais altos
    @Query("SELECT v FROM Venda v ORDER BY v.valorTotal DESC")
    List<Venda> DezMaioresVendas();*/
    List<Venda> findTop10ByOrderByVlTotalDesc();

}
