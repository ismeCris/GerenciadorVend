package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT c FROM Cliente c WHERE c.idade BETWEEN :minIdade AND :maxIdade")
    public List<Cliente> findIdade18a35(@Param("minIdade") int minIdade, @Param("maxIdade") int maxIdade);

}
