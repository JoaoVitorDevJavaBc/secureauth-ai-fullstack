package com.CRUD.crud.controller;

import com.CRUD.crud.dto.LoginDTO;
import com.CRUD.crud.model.Usuario;
import com.CRUD.crud.service.TokenService;
import com.CRUD.crud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public List<Usuario> listarTodosUsuarios() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário cadastrado com sucesso! Verifique seu e-mail.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            String mensagem = usuarioService.validarLogin(loginDTO.getEmail(), loginDTO.getCodigo());

            String token = tokenService.gerarToken(loginDTO.getEmail());

            return ResponseEntity.ok(token);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
