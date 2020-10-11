package br.com.henrique.teste.dev.service;

import java.util.List;
import java.util.Optional;

import br.com.henrique.teste.dev.utils.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.henrique.teste.dev.modal.vo.PaisVO;
import br.com.henrique.teste.dev.repository.PaisRepository;

/**
 * Classe Service contendo regras de negocio de {@link PaisVO}
 */
@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private Properties properties;

    /**
     * Retorna todos os Paises persistidos no banco de dados
     *
     * @return {@List<PaisVO>}
     */
    public List<PaisVO> findAll() {
        return paisRepository.findAll();
    }

    /**
     * Retorna todos os Paises que contem no "nome" o filtro informado por parametro
     *
     * @return {@List<PaisVO>}
     */
    public List<PaisVO> findByNome(String filtro) {
        return paisRepository.findByNomeContainingIgnoreCase(filtro);
    }

    /**
     * Metodo responsavel pela exclusao de {@link PaisVO}
     *
     * @return {@Boolean} True caso a exclusao foi efetuada
     */
    public Boolean delete(Long id) {
        boolean deletou = false;
        if (paisRepository.existsById(id)) {
            paisRepository.deleteById(id);
            deletou = true;
        }
        return deletou;
    }

    /**
     * Metodo responsavel pela persistencia de {@link PaisVO}</br>
     * - Caso o campo ID do VO seja null || 0, e persistido como nome </br>
     * - Caso contrario e pesquisado no banco a existencia de um VO com o mesmo id, se existir e realizado o update da entidade
     *
     * @param {@link PaisVO}
     * @return {@link PaisVO}
     */
    public PaisVO saveOrUpdate(PaisVO paisVO) {
        Long id = paisVO.getId();
        if (null == id || 0 == id) {
            paisVO.setId(null);
        } else {
            this.existePaisNaBase(id);
        }
        return paisRepository.saveAndFlush(paisVO);
    }

    private PaisVO existePaisNaBase(final Long id) {
        Optional<PaisVO> paisOptional = paisRepository.findById(id);
        return paisOptional.orElseThrow(() -> new RuntimeException(properties.idNaoEncontrado));
    }
}
