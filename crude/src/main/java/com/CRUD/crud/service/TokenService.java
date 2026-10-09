package com.CRUD.crud.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private final String chaveSecreta = "SUA_SENHA_SECRETA";

    public String gerarToken(String emailDoUsuario) {
        Instant instanteAtual = LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
        Instant instanteExpiracao = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

        Date dataAtual = Date.from(instanteAtual);
        Date dataExpiracao = Date.from(instanteExpiracao);

        return Jwts.builder()
                .claim("roles", java.util.List.of("ROLE_USER"))
                .setSubject(emailDoUsuario)
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, chaveSecreta.getBytes())
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(chaveSecreta.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
