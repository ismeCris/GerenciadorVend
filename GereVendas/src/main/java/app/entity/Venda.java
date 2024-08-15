package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "A descrição da venda é obrigatória.")
	private String descricao;
	private Double vlTotal;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	@JsonIgnoreProperties("vendas")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@NotNull(message = "Um cliente deve estar vinculado à venda.")
	private Cliente cliente;
	
	@ManyToMany
	@JoinTable(name="venda_tem_produto")
	@NotEmpty(message = "A lista de produtos não pode estar vazia.")
	private List<Produto> produtos;
	

}
