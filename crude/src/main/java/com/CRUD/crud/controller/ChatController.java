package com.CRUD.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {


    @PostMapping("/enviar")
    public ResponseEntity<String> enviarMensagem(@RequestBody Map<String, String> body) {
        try {
            String mensagemDoUsuario = body.get("mensagem");

            String respostaSimuladaIA = "[SISTEMA SEGURO - MOCK IA] Olá, João Vitor! Recebi sua mensagem: '" + mensagemDoUsuario + "'.\n" +
                    "O filtro de segurança validou seu Token JWT com sucesso e confirmou que seu e-mail possui a permissão ROLE_USER!";

            return ResponseEntity.ok(respostaSimuladaIA);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro no processamento do chat: " + e.getMessage());
        }
    }
}
