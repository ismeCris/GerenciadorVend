package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message ="O nome do Cliente é obrigatória. ")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ]+(\\s+[A-Za-zÀ-ÿ]+)+$")
	private String nome;
	
	@Email
	private String email;
	
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")
	private String telefone;

	
	@CPF
	private String cpf;
	
	@Min(value = 0, message ="A idade nao pode ser negativa.")
	private int idade;
	
	@NotBlank(message ="O endereço do cliente é obrigatória. ")
	private String  endereco;
	
	@Pattern(regexp = "\\d{5}-\\d{3}")
	private String cep;

	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Venda> vendas;


}
