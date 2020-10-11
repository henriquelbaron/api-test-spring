package br.com.henrique.teste.dev.repository;

import br.com.henrique.teste.dev.modal.vo.TokenVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.henrique.teste.dev.modal.vo.UsuarioVO;

import java.util.Optional;

/**
 * Repositorio responsavel pela manipulacao da entidade {@link UsuarioVO} no banco de dados
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioVO, Long> {

    /**
     * Consulta no banco, usuario que possua o login e senha fornecidos por parametro
     * @param {@link String} login
     * @param {@link String} senha
     * @return {@link Optional<UsuarioVO>}
     */
    Optional<UsuarioVO> findByLoginAndSenha(String login, String senha);
}
