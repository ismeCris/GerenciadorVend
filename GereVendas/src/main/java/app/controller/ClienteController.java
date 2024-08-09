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
import org.springframework.web.bind.annotation.RestController;
import app.entity.Cliente;
import app.entity.Produto;
import app.service.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;

	@PostMapping("/save")
	public ResponseEntity<String>save(@RequestBody Cliente cliente){
		try {
			String msn = this.clienteService.save(cliente);
			return new ResponseEntity<>(msn, HttpStatus.OK);
		} catch (Exception e) {
			 return new ResponseEntity<>("Deu Erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{index}")
	public ResponseEntity<String> update(@RequestBody Cliente cliente, @PathVariable int index){
		try {
			String msn = this.clienteService.update(cliente, index);
			return new ResponseEntity<>(msn, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Deu erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/findById/{index}")
	public ResponseEntity<Cliente> findById(@PathVariable int index) {
		try {
			Cliente bibliotecaEntity = this.clienteService.findById(index);
			return new ResponseEntity<>(bibliotecaEntity, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Cliente>> findAll() {
		try {
			List<Cliente> lista = this.clienteService.findALl();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{index}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		try {
			String msn = this.clienteService.delete(id);
			return new ResponseEntity<>(msn, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	

}
