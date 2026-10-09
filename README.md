# SecureAuth AI - Full-Stack Ecosystem

Sistema full-stack corporativo de autenticação multifator (2FA) e barramento seguro de inteligência artificial.

##  Tecnologias Utilizadas

- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT (Json Web Token), JavaMailSender (SMTP)
- **Frontend:** React, Vite, JavaScript, React Router DOM
- **Banco de Dados:** H2 Database (Memória/Desenvolvimento)
- **Ferramentas de Teste:** Postman

##  Arquitetura e Fluxo de Segurança

1. **Solicitação de Acesso (Passo 1):** O cliente envia o e-mail via frontend. O backend gera um código numérico aleatório de 6 dígitos, salva o hash no banco com expiração de 5 minutos e dispara o código real para o e-mail do usuário via SMTP do Gmail.
2. **Autenticação e Emissão de Token (Passo 2):** O usuário insere o código recebido. O backend valida a credencial e emite um **Token JWT** seguro contendo as permissões de acesso (`ROLE_USER`).
3. **Barramento Protegido (Chat IA):** O endpoint de comunicação `/chat/enviar` exige o cabeçalho `Authorization: Bearer <TOKEN_JWT>`. O `SecurityFilter` intercepta a requisição, valida o token e libera o processamento da mensagem apenas para usuários autenticados.

##  Como Executar o Projeto Localmente

### Pré-requisitos
- JDK 17 ou superior instalado
- Node.js LTS instalado

### 1. Executando o Backend (Java)
No IntelliJ ou terminal, configure as seguintes variáveis de ambiente locais antes de rodar a aplicação:
```env
GMAIL_USER=seu_email@gmail.com
GMAIL_APP_PASSWORD=sua_senha_de_aplicativo_do_gmail
JWT_SECRET=sua_chave_secreta_com_mais_de_32_caracteres
```
Execute a classe principal `CrudeApplication.java`. O servidor backend subirá em `https://localhost:8443`.

### 2. Executando o Frontend (React)
Navegue até a pasta do frontend e execute os comandos:
```bash
cd muzovu-front
npm install
npm run dev
```
O servidor frontend subirá em `http://localhost:5173`.

> **Nota de Segurança:** Por utilizar HTTPS local no backend, acesse `https://localhost:8443/usuarios` no navegador uma primeira vez e clique em "Avançado -> Prosseguir para localhost (inseguro)" para liberar a comunicação de portas (CORS) entre o React e o Spring Boot.
