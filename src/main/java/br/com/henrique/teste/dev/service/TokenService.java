package br.com.henrique.teste.dev.service;

import br.com.henrique.teste.dev.utils.Properties;
import br.com.henrique.teste.dev.exception.TokenException;
import br.com.henrique.teste.dev.exception.UnauthorizedException;
import br.com.henrique.teste.dev.modal.vo.TokenVO;
import br.com.henrique.teste.dev.modal.vo.UsuarioVO;
import br.com.henrique.teste.dev.repository.TokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Classe Service contendo regras de negocio da manipulacao de tokens
 */
@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private Properties properties;

    /**
     * Metodo responsavel pela validacao de acesso do usuario
     *
     * @param token                String contendo token
     * @param validarAdministrador True para caso queira validar se usuario e administrador
     * @return {@link Boolean} true se for valido
     */
    public Boolean verificaAutorizacao(final String token, final Boolean validarAdministrador) {
        TokenVO tokenVO = tokenRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException());
        if (validarAdministrador && !tokenVO.getAdministrador()) {
            throw new UnauthorizedException();
        }
        if (LocalDateTime.now().isAfter(tokenVO.getExpiracao())) {
            throw new TokenException(properties.tokenExpirado);
        }
        return true;
    }

    /**
     * Metodo responsavel pela geracao de um novo {@link TokenVO}
     *
     * @param {@link UsuarioVO} que esta requisitando o token
     * @return {@link TokenVO} persistido no banco
     */
    public TokenVO gerarToken(final UsuarioVO usuarioVO) {
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(this.gerarHash());
        tokenVO.setLogin(usuarioVO.getLogin());
        tokenVO.setExpiracao(this.gerarValidadeToken());
        tokenVO.setAdministrador(usuarioVO.getAdministrador());
        tokenRepository.save(tokenVO);
        return tokenVO;
    }

    /**
     * Metodo responsavel pela renovacao de um Token </br>
     * caso exista o token informado, e atualizado o atributo expiracao e retorna True
     *
     * @param {@link String} token a ser renovado
     * @return {@link Boolean}
     */
    public Boolean renovarToken(final String token) {
        Optional<TokenVO> tokenOptional = tokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) return false;
        TokenVO tokenVO = tokenOptional.get();
        tokenVO.setExpiracao(this.gerarValidadeToken());
        tokenRepository.save(tokenVO);
        return true;
    }


    private String gerarHash() {
        final String stringRandomica = RandomStringUtils.random(36, true, true);
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(stringRandomica.getBytes(), 0, stringRandomica.length());
            byte[] digest = m.digest();
            return new BigInteger(1, digest).toString(12);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocalDateTime gerarValidadeToken() {
        return LocalDateTime.now().plusMinutes(properties.minutosValidadeToken);
    }
}
