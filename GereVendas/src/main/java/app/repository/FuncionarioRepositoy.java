package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Funcionario;

@Repository
public interface FuncionarioRepositoy extends JpaRepository<Funcionario, Long>{

}
