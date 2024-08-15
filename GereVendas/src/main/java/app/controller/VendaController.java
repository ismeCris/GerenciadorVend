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

import app.entity.Produto;
import app.entity.Venda;
import app.service.ProdutoService;
import app.service.VendaService;
@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Venda venda) {
        try {
            String msn = this.vendaService.save(venda);
            return new ResponseEntity<>(msn, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu Erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Venda venda, @PathVariable Long id) {
        try {
            String msn = this.vendaService.update(venda, id);
            return new ResponseEntity<>(msn, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu erro! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Venda> findById(@PathVariable Long id) {
        try {
            Venda venda = this.vendaService.findById(id);
            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Venda>> findAll() {
        try {
            List<Venda> lista = this.vendaService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String msn = this.vendaService.delete(id);
            return new ResponseEntity<>(msn, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /*-----------------------------------------------------------------------*/
		@GetMapping("/findByClienteNomeContains")
	public ResponseEntity<List<Venda>> findByClienteNomeContains(@RequestParam String nome) {
		try {
			List<Venda> vendas = this.vendaService.VendasPorNomeDeCliente(nome);
			return new ResponseEntity<>(vendas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
		
	@GetMapping("/findByFuncionarioNomeContains")
	public ResponseEntity<List<Venda>> findByFuncionarioNomeContains(@RequestParam String nome) {
		try {
			List<Venda> vendas = this.vendaService.VendasPorNomeDeFuncionario(nome);
			return new ResponseEntity<>(vendas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	

    @GetMapping("/findTop10ByOrderByValorTotalDesc")
	public ResponseEntity<List<Venda>> findTop10ByOrderByValorTotalDesc() {
		try {
			List<Venda> vendas = this.vendaService.DezMaioresVendas();
			return new ResponseEntity<>(vendas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

}