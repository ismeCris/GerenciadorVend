package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

    @Query("SELECT v FROM Venda v WHERE v.cliente.nome LIKE %:nome%")
    List<Venda> findByClienteNomeContaining(@Param("nome") String nome);

    @Query("SELECT v FROM Venda v WHERE v.funcionario.nome LIKE %:nome%")
    List<Venda> findByFuncionarioNomeContaining(@Param("nome") String nome);

    @Query("SELECT v FROM Venda v ORDER BY v.vlTotal DESC")
    List<Venda> findTop10ByOrderByVlTotalDesc();	
	

}
