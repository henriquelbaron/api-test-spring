package br.com.henrique.teste.dev.modal.vo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Value Object de Token
 */
@Data
@Entity(name = "token")
public class TokenVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
	@SequenceGenerator(name="token_generator", sequenceName = "seq_token", allocationSize=1)
	private Long id;
	private String token;
	private String login;
	private LocalDateTime expiracao;
	private Boolean administrador;
}
