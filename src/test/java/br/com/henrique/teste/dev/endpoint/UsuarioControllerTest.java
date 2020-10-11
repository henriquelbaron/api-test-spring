package br.com.henrique.teste.dev.endpoint;

import br.com.henrique.teste.dev.modal.dto.LoginResponseDTO;
import br.com.henrique.teste.dev.modal.vo.TokenVO;
import br.com.henrique.teste.dev.repository.TokenRepository;
import br.com.henrique.teste.dev.utils.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_SENHA = "senha";
    public static final String LOGIN_ADMIN = "admin";
    public static final String SENHA_ADM = "suporte";
    public static final String LOGIN_CONVIDADO = "convidado";
    public static final String SENHA_CONVIDADO = "manager";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void autenticarUsuarioADM() throws Exception {
        MultiValueMap<String, String> params = getParams(PARAM_LOGIN, LOGIN_ADMIN, PARAM_SENHA, SENHA_ADM);
        MvcResult mvcResult = mockMvc.perform(post(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_AUTENTICAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        LoginResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponseDTO.class);
        Assertions.assertFalse(responseDTO.getToken().isEmpty());
        Assertions.assertTrue(responseDTO.getAdministrador());
    }


    @Test
    void autenticarUsuarioConvidado() throws Exception {
        MultiValueMap<String, String> params = getParams(PARAM_LOGIN, LOGIN_CONVIDADO, PARAM_SENHA, SENHA_CONVIDADO);
        MvcResult mvcResult = mockMvc.perform(post(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_AUTENTICAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        LoginResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponseDTO.class);
        Assertions.assertFalse(responseDTO.getToken().isEmpty());
        Assertions.assertFalse(responseDTO.getAdministrador());
    }

    @Test
    void autenticarUsuarioLoginInvalido() throws Exception {
        MultiValueMap<String, String> params = getParams(PARAM_LOGIN, "loginErrado", PARAM_SENHA, SENHA_CONVIDADO);
        mockMvc.perform(post(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_AUTENTICAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void autenticarUsuarioSenhaInvalido() throws Exception {
        MultiValueMap<String, String> params = getParams(PARAM_LOGIN, LOGIN_CONVIDADO, PARAM_SENHA, "senhaErrada");
        mockMvc.perform(post(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_AUTENTICAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    private MultiValueMap<String, String> getParams(String paramLogin, String loginAdmin, String paramSenha, String senhaAdm) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(paramLogin, loginAdmin);
        params.add(paramSenha, senhaAdm);
        return params;
    }

    @Test
    void renovarToken() throws Exception {
        List<TokenVO> tokenExpirado = tokenRepository.findTokenExpirado();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", tokenExpirado.get(0).getToken());
        mockMvc.perform(get(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_RENOVARTOKEN)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void renovarTokenInexistente() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", "SDHJFGSFGSJDHGFDFH");
        mockMvc.perform(get(Constantes.USUARIO_ENDPOINT + Constantes.USUARIO_RENOVARTOKEN)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }
}