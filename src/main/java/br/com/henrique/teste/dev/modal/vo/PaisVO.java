package br.com.henrique.teste.dev.modal.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Value Object de Pais
 */
@Data
@Entity(name = "pais")
public class PaisVO  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_generator")
	@SequenceGenerator(name="pais_generator", sequenceName = "seq_pais", allocationSize=1)
	private Long id;
	private String nome;
	private String sigla;
	private String gentilico;
}
