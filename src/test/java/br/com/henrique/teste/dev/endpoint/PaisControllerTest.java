package br.com.henrique.teste.dev.endpoint;

import br.com.henrique.teste.dev.modal.dto.LoginResponseDTO;
import br.com.henrique.teste.dev.modal.vo.PaisVO;
import br.com.henrique.teste.dev.repository.PaisRepository;
import br.com.henrique.teste.dev.service.UsuarioService;
import br.com.henrique.teste.dev.utils.Constantes;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaisControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private final String TOKEN_INVALIDO = "lliqglkuyQDIULHGHYUIV8972213Oo213";


    @Test
    void listarTodos() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getToken());
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_LISTAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void listarTodosTokenInvalido() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", TOKEN_INVALIDO);
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_LISTAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void pesquisar() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getToken());
        params.add("filtro", "a");
        MvcResult mvcResult = mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_PESQUISAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<PaisVO> paises = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<PaisVO>>() {
                });
        Assertions.assertFalse(paises.isEmpty());
        Assertions.assertEquals(paises.get(0).getSigla(), "BR");
    }

    @Test
    void pesquisarTokenInvalido() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", TOKEN_INVALIDO);
        params.add("filtro", "a");
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_PESQUISAR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void removerPais() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getTokenADM());
        params.add("id", "3");
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_EXCLUIR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void removerPaisIdInvalido() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getTokenADM());
        params.add("id", "523523");
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_EXCLUIR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    void removerPaisSemPermisao() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getToken());
        params.add("id", "3");
        mockMvc.perform(get(Constantes.PAIS_ENDPOINT + Constantes.PAIS_EXCLUIR)
                .params(params)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void persistirNovo() throws Exception {
        PaisVO novoPais = getNovoPais();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getTokenADM());
        MvcResult mvcResult = mockMvc.perform(post(Constantes.PAIS_ENDPOINT + Constantes.PAIS_SALVAR)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoPais))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        PaisVO paisSalvo = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaisVO.class);
        Assertions.assertNotEquals(paisSalvo.getId(), novoPais.getId());
        Assertions.assertEquals(paisSalvo.getSigla(), novoPais.getSigla());
        Assertions.assertEquals(paisSalvo.getGentilico(), novoPais.getGentilico());
        Assertions.assertEquals(paisSalvo.getNome(), novoPais.getNome());
    }

    @Test
    void persistirPaisComUsuarioSemPermisao() throws Exception {
        PaisVO novoPais = getNovoPais();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getToken());
        mockMvc.perform(post(Constantes.PAIS_ENDPOINT + Constantes.PAIS_SALVAR)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoPais))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void alterarPaisIdInvalido() throws Exception {
        PaisVO pais = getNovoPais();
        pais.setId(1234L);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getTokenADM());
        mockMvc.perform(post(Constantes.PAIS_ENDPOINT + Constantes.PAIS_SALVAR)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pais))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void alterarPais() throws Exception {
        PaisVO paisBase = paisRepository.findById(1L).get();
        paisBase.setNome("Nome Alterado");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", getTokenADM());
        mockMvc.perform(post(Constantes.PAIS_ENDPOINT + Constantes.PAIS_SALVAR)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paisBase))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        PaisVO paisSalvo = paisRepository.findById(1L).get();
        Assertions.assertEquals(paisSalvo.getId(), paisBase.getId());
        Assertions.assertEquals(paisSalvo.getSigla(), paisBase.getSigla());
        Assertions.assertEquals(paisSalvo.getGentilico(), paisBase.getGentilico());
        Assertions.assertEquals(paisSalvo.getNome(), paisBase.getNome());
    }

    private String getTokenADM() {
        LoginResponseDTO loginResponseDTOADM = usuarioService.autenticarLogin("admin", "suporte");
        return loginResponseDTOADM.getToken();
    }

    private String getToken() {
        LoginResponseDTO loginResponseDTO = usuarioService.autenticarLogin("convidado", "manager");
        return loginResponseDTO.getToken();
    }

    private PaisVO getNovoPais() {
        PaisVO paisVO = new PaisVO();
        paisVO.setId(0L);
        paisVO.setSigla("EUA");
        paisVO.setGentilico("Americano");
        paisVO.setNome("Estados Unidos da America");
        return paisVO;
    }
}