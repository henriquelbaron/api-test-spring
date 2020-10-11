package br.com.henrique.teste.dev.service;

import br.com.henrique.teste.dev.utils.Properties;
import br.com.henrique.teste.dev.exception.LoginException;
import br.com.henrique.teste.dev.modal.dto.LoginResponseDTO;
import br.com.henrique.teste.dev.modal.vo.TokenVO;
import br.com.henrique.teste.dev.modal.vo.UsuarioVO;
import br.com.henrique.teste.dev.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe Service contendo regras de negocio da manipulacao do usuario
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Properties properties;

    /**
     * Metodo responsavel pelo login do usuario, caso os parametros informados sejam validos e retornado {@link LoginResponseDTO} contendo token
     *
     * @param {@link String} login
     * @param {@link String} senha
     * @return {@link LoginResponseDTO}
     */
    public LoginResponseDTO autenticarLogin(final String login, final String senha) {
        final boolean autenticado = true;
        final UsuarioVO usuarioVO = usuarioRepository.findByLoginAndSenha(login, senha)
                .orElseThrow(() -> new LoginException(properties.loginOuSenhaInvalido));
        final TokenVO token = tokenService.gerarToken(usuarioVO);
        return new LoginResponseDTO(autenticado,
                usuarioVO.getLogin(),
                usuarioVO.getAdministrador(),
                token.getToken(),
                usuarioVO.getNome());
    }

    /**
     * Atualiza o tempo de expiracao do token
     *
     * @param {@link String} token
     * @return Boolean
     */
    public Boolean renovarToken(final String token) {
        return tokenService.renovarToken(token);
    }

}
