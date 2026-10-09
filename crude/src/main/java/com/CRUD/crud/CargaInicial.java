package com.CRUD.crud;

import com.CRUD.crud.model.Usuario;
import com.CRUD.crud.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicial implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public CargaInicial(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {

        Usuario usuario = new Usuario();
        usuario.setEmail("seu_email");

        usuarioService.salvar(usuario);
    }
}
