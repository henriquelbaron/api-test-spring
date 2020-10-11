package br.com.henrique.teste.dev.endpoint;

import br.com.henrique.teste.dev.exception.UnauthorizedException;
import br.com.henrique.teste.dev.modal.vo.PaisVO;
import br.com.henrique.teste.dev.service.PaisService;
import br.com.henrique.teste.dev.service.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.henrique.teste.dev.utils.Constantes.*;

@RestController
@RequestMapping(PAIS_ENDPOINT)
@ApiOperation(value = "Pais Endpoints")
public class PaisController {

    @Autowired
    private PaisService paisService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "Listar todos Países")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de Países"),
            @ApiResponse(code = 401, message = "Token inválido/expirado ou Usuário sem permissões")})
    @GetMapping(value = PAIS_LISTAR)
    public List<PaisVO> listarTodos(@Validated @RequestParam String token) {
        this.validaToken(token, false);
        return paisService.findAll();
    }

    @ApiOperation(value = "Pesquisar Países")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Listar com os Países que possuem no nome o filtro informado"),
            @ApiResponse(code = 401, message = "Token inválido/expirado ou Usuário sem permissões")})
    @GetMapping(value = PAIS_PESQUISAR)
    public List<PaisVO> pesquisar(@Validated @RequestParam String token, @RequestParam String filtro) {
        this.validaToken(token, false);
        return paisService.findByNome(filtro);
    }

    @ApiOperation(value = "Excluir País")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Booleano confirmando ou não a Exclusão"),
            @ApiResponse(code = 401, message = "Token inválido/expirado ou Usuário sem permissões")})
    @GetMapping(value = PAIS_EXCLUIR)
    public Boolean removerPais(@Validated @RequestParam String token, @RequestParam Long id) {
        this.validaToken(token, true);
        return paisService.delete(id);
    }

    @ApiOperation(value = "Salvar/Alterar País")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "País que foi inserido/alterado na base de dados"),
            @ApiResponse(code = 401, message = "Token inválido/expirado ou Usuário sem permissões"),
            @ApiResponse(code = 400, message = "ID informado inválido!")})
    @PostMapping(value = PAIS_SALVAR)
    public PaisVO salvarAlterar(@Validated @RequestParam String token, @RequestBody PaisVO paisVO) {
        this.validaToken(token, true);
        return paisService.saveOrUpdate(paisVO);
    }

    private void validaToken(final String token, final Boolean validarAdministrador) {
        if (!tokenService.verificaAutorizacao(token, validarAdministrador)) throw new UnauthorizedException();
    }
}
