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
	
	 @NotBlank(message = "O nome é obrigatório e deve conter pelo menos duas palavras e um espaço.")
	 @Pattern(regexp = "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)+$", message = "O nome deve conter pelo menos duas palavras e um espaço.")

	    private String nome;

	
	 @Email(message = "O email deve ser válido.")
	    private String email;
	 
	 @NotBlank(message = "O telefone é obrigatório.")
	    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve seguir o padrão: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
	    private String telefone;

	    @NotBlank(message = "O CPF é obrigatório.")
	  @CPF(message = "O CPF deve ser válido.")
	    private String cpf;
	
	@Min(value = 0, message ="A idade nao pode ser negativa.")
	private int idade;
	
	@NotBlank(message ="O endereço do cliente é obrigatória. ")
	private String  endereco;
	
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve seguir o padrão XXXXX-XXX.")
	private String cep;

	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Venda> vendas;


}
