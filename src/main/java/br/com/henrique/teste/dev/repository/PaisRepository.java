package br.com.henrique.teste.dev.repository;

import br.com.henrique.teste.dev.modal.vo.PaisVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio responsavel pela manipulacao da entidade {@link PaisVO} no banco de dados
 */
@Repository
public interface PaisRepository extends JpaRepository<PaisVO, Long> {
    /**
     * Busca no banco Paises que contenhan no nome o filtro informado por parametro
     *
     * @param {@link String} filtro
     * @return {@link List<PaisVO>}
     */
    List<PaisVO> findByNomeContainingIgnoreCase(String filtro);
}
