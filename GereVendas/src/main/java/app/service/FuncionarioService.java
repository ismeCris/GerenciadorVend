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
	public FuncionarioRepositoy funcionarioRepository;
	
	public String save(Funcionario funcionario ) {
		this.funcionarioRepository.save(funcionario);
		return "Funcionario Cadastrado com sucesso";
	}
	
	 public String update(Funcionario funcionario, Long id) {
	        if (!funcionarioRepository.existsById(id)) {
	            throw new RuntimeException("Funcionário com ID " + id + " não encontrado");
	        }
	        funcionario.setId(id);
	        this.funcionarioRepository.save(funcionario);
	        return "Atualizado com sucesso";
	    }
	public Funcionario findById(long id) {
		Optional<Funcionario> optional = this.funcionarioRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else
			return null;
	}
	public List<Funcionario>findAll(){
		return this.funcionarioRepository.findAll();
	}
	
	public String delete(Long id) {
		this.funcionarioRepository.deleteById(id);
		return "Funcionario deletado com sucesso!";
	}

}
