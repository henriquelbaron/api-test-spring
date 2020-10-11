package br.com.henrique.teste.dev.modal.vo;

import lombok.Data;

import javax.persistence.*;

/**
 * Value Object de Pais
 */
@Data
@Entity(name = "usuario")
public class UsuarioVO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_generator")
    @SequenceGenerator(name = "usuario_generator", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String login;
    private String senha;
    private String nome;
    private Boolean administrador;
}
