package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Funcionario;
import app.repository.FuncionarioRepositoy;

@Service
public class FuncionarioService {
	@Autowired
	public FuncionarioRepositoy funcionarioRepsitory;
	
	public String save(Funcionario funcionario ) {
		this.funcionarioRepsitory.save(funcionario);
		return "Funcionario Cadastrado com sucesso";
	}
	
	public String update( Funcionario funcionario, Long id) {
		funcionario.setId(id);
		this.funcionarioRepsitory.save(funcionario);
		return "Atualizado com sucesso!";
	}
	public Funcionario findById(long id) {
		Optional<Funcionario> optional = this.funcionarioRepsitory.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
	}
	public List<Funcionario>findAll(){
		return this.funcionarioRepsitory.findAll();
	}
	
	public String delete(Long id) {
		this.funcionarioRepsitory.deleteById(id);
		return "Funcionario deletado com sucesso!";
	}

}
