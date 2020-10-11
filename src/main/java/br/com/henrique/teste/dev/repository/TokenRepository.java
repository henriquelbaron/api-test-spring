package br.com.henrique.teste.dev.repository;

import br.com.henrique.teste.dev.modal.vo.PaisVO;
import br.com.henrique.teste.dev.modal.vo.TokenVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio responsavel pela manipulacao da entidade {@link TokenVO} no banco de dados
 */
@Repository
public interface TokenRepository extends JpaRepository<TokenVO, Long> {
    /**
     * Consulta no banco por entidade que possua o token passado por parametro
     *
     * @param {@link String} token
     * @return {@link Optional<TokenVO>}
     */
    Optional<TokenVO> findByToken(String token);

    /**
     * Consulta Tokens que estejam com a data de expiracao <= 5 minutos que a data atual
     *
     * @return {@link List<TokenVO>}
     */
    @Query(value = "SELECT * FROM token WHERE expiracao <= NOW() - INTERVAL 5 MINUTE;", nativeQuery = true)
    List<TokenVO> findTokenExpirado();
}
