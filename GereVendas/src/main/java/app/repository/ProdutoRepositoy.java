package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Produto;

@Repository
public interface ProdutoRepositoy extends JpaRepository<Produto, Long>{
	List<Produto> findTop10ByOrderByPrecoDesc();


}
