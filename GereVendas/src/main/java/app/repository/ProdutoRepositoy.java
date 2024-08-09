package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Produto;

public interface ProdutoRepositoy extends JpaRepository<Produto, Long>{

}
