package com.CRUD.crud.service;

import com.CRUD.crud.model.Usuario;
import com.CRUD.crud.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        String codigoAleatorio = String.format("%06d", new Random().nextInt(999999));

        usuario.setCodigoVerificacao(codigoAleatorio);
        usuario.setExpiracaoCodigo(LocalDateTime.now().plusMinutes(5));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        try {
            emailService.enviarEmail(
                    usuarioSalvo.getEmail(),
                    "Seu Código de Verificação",
                    "Seu código de acesso de 6 dígitos é: " + codigoAleatorio
            );
        } catch (Exception e) {
            System.err.println("Falha ao disparar o e-mail: " + e.getMessage());
        }

        return usuarioSalvo;
    }

    public String validarLogin(String email, String codigo) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (usuario.getCodigoVerificacao() != null &&
                    usuario.getCodigoVerificacao().equals(codigo) &&
                    usuario.getExpiracaoCodigo().isAfter(LocalDateTime.now())) {

                usuario.setCodigoVerificacao(null);
                usuario.setExpiracaoCodigo(null);
                usuarioRepository.save(usuario);

                return "Login efetuado com sucesso!";
            }
        }
        throw new RuntimeException("E-mail ou código de verificação inválido/expirado!");
    }
}
