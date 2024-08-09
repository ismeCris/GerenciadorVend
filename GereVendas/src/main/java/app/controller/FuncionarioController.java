package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Funcionario;
import app.service.FuncionarioService;


@RestController
@RequestMapping("/api/Funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Funcionario funcionario) {
	    try {
	        String msn = this.funcionarioService.save(funcionario);
	        return new ResponseEntity<>(msn, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Deu Erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}


	@PutMapping("/update/{index}")
	public ResponseEntity<String> update(@RequestBody Funcionario funcionario, @PathVariable  Long id) {
		try {
			String msn = this.funcionarioService.update(funcionario,  id);
			return new ResponseEntity<>(msn, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Deu erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById/{index}")
	public ResponseEntity<Funcionario> findById(@PathVariable int index) {
		try {
			Funcionario bibliotecaEntity = this.funcionarioService.findById(index);
			return new ResponseEntity<>(bibliotecaEntity, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Funcionario>> findAll() {
		try {
			List<Funcionario> lista = this.funcionarioService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{index}")
	public ResponseEntity<String> delete(@PathVariable int index) {
		try {
			String mensagem = this.funcionarioService.delete(index);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 @GetMapping("/filtrar")
	    public List<BibliotecaEntity> filtrarPorNome(@RequestParam String prefixo) {
	        return bibliotecaService.findByNomeStartingWith(prefixo);
	    }*/
}
