package br.com.henrique.teste.dev.endpoint;

import br.com.henrique.teste.dev.modal.dto.LoginResponseDTO;
import br.com.henrique.teste.dev.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.henrique.teste.dev.utils.Constantes.*;

@RestController
@RequestMapping(USUARIO_ENDPOINT)
@ApiOperation(value = "Usuario Endpoints")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Autenticar Usuário")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Usuário contendo token de autenticação"),
            @ApiResponse(code = 401, message = "Login/Senha Inválida")})
    @PostMapping(value = USUARIO_AUTENTICAR)
    public LoginResponseDTO autenticarUsuario(@Validated @RequestParam String login, @Validated @RequestParam String senha) {
        return usuarioService.autenticarLogin(login, senha);
    }

    @ApiOperation(value = "Renovar Token do Usuário")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Boolean informando se a autenticação foi efetuada"),})
    @GetMapping(value = USUARIO_RENOVARTOKEN)
    public Boolean renovarToken(@Validated @RequestParam String token) {
        return usuarioService.renovarToken(token);
    }
}
