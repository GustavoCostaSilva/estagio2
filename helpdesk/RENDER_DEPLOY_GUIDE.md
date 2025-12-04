# 🚀 GUIA DE DEPLOY - RENDER.COM

## 📋 PRÉ-REQUISITOS
- ✅ Código no GitHub (seu repositório: GustavoCostaSilva/estagio2)
- ✅ Banco Supabase configurado
- ✅ `ddl-auto=update` configurado

---

## 🔧 PASSO 1: PREPARAR O REPOSITÓRIO

### 1.1 Criar arquivo de build para Render
Crie o arquivo `render-build.sh` na raiz do projeto **helpdesk**:

```bash
#!/usr/bin/env bash
# Render build script

echo "Installing dependencies and building application..."
./mvnw clean package -DskipTests
```

### 1.2 Tornar o script executável
```bash
chmod +x render-build.sh
```

### 1.3 Fazer commit e push
```bash
git add .
git commit -m "Configure Render deployment"
git push origin main
```

---

## 🌐 PASSO 2: CRIAR WEB SERVICE NO RENDER

### 2.1 Acessar Render
1. Acesse: https://render.com
2. Faça login (ou crie conta gratuita)
3. Clique em **"New +"** → **"Web Service"**

### 2.2 Conectar Repositório
1. Conecte sua conta do GitHub
2. Selecione o repositório: **GustavoCostaSilva/estagio2**
3. Clique em **"Connect"**

### 2.3 Configurar o Serviço

**Basic Settings:**
- **Name:** `helpdesk-api` (ou qualquer nome)
- **Region:** `Oregon (US West)` (mais próximo do Supabase)
- **Branch:** `main`
- **Root Directory:** `helpdesk`
- **Runtime:** `Java`

**Build Settings:**
- **Build Command:** `./mvnw clean package -DskipTests`
- **Start Command:** `java -Dserver.port=$PORT -jar target/helpdesk-0.0.1-SNAPSHOT.jar`

**Instance Type:**
- Selecione **"Free"** (512 MB RAM)

---

## 🔐 PASSO 3: CONFIGURAR VARIÁVEIS DE AMBIENTE

No Render, vá em **"Environment"** e adicione:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `dev` |
| `DB_PASSWORD` | `863090Gu.!!` |
| `JWT_SECRET` | `sua_chave_jwt_secreta_aqui_minimo_256bits` |
| `PORT` | `8080` |

**💡 Dica:** Gere um JWT_SECRET forte:
```bash
openssl rand -base64 64
```

---

## 🚀 PASSO 4: DEPLOY

1. Clique em **"Create Web Service"**
2. Aguarde o build (leva ~5-10 minutos na primeira vez)
3. Quando aparecer **"Live"**, sua API está no ar! 🎉

**Sua URL será algo como:**
```
https://helpdesk-api.onrender.com
```

---

## ✅ PASSO 5: TESTAR A API

### 5.1 Verificar Saúde
```bash
curl https://helpdesk-api.onrender.com/login
```

### 5.2 Testar Login (quando tiver usuários)
```bash
curl -X POST https://helpdesk-api.onrender.com/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@mail.com","senha":"admin"}'
```

---

## 🔄 PASSO 6: ATUALIZAR CORS PARA PRODUÇÃO

Após fazer deploy do frontend no Netlify, atualize o `SecurityConfig.java`:

```java
// Substitua "*" pela URL do Netlify
configuration.setAllowedOrigins(Arrays.asList(
    "https://seu-app.netlify.app",
    "http://localhost:4200"  // Para testes locais
));
configuration.setAllowCredentials(true);  // Ative se necessário
```

---

## 🐛 TROUBLESHOOTING

### Build falha
- Verifique se `Root Directory` está como `helpdesk`
- Certifique-se que `mvnw` tem permissão de execução

### Aplicação não inicia
- Verifique os logs no Render Dashboard
- Confirme variáveis de ambiente
- Verifique se a senha do Supabase está correta

### Erro 502/503
- Instância gratuita "dorme" após inatividade
- Primeira requisição pode demorar ~1 minuto

### Conexão com Supabase falha
- Verifique IP whitelist no Supabase (deve estar liberado)
- Confirme a senha no DB_PASSWORD

---

## 📱 PRÓXIMOS PASSOS

1. ✅ API funcionando no Render
2. 🔲 Deploy do Frontend Angular no Netlify
3. 🔲 Atualizar CORS com URL do Netlify
4. 🔲 Configurar domínio customizado (opcional)

---

## 💰 PLANO GRATUITO - LIMITAÇÕES

- **Sleep após inatividade:** App "dorme" após 15 min sem requests
- **750 horas/mês:** Suficiente para desenvolvimento
- **512 MB RAM:** Adequado para Spring Boot simples
- **Cold start:** Primeira requisição pode demorar ~30s

---

## 🔗 LINKS ÚTEIS

- **Render Dashboard:** https://dashboard.render.com
- **Supabase Dashboard:** https://supabase.com/dashboard/project/zmfmjjrpwllmacmvajbr
- **Logs do Render:** Acesse via Dashboard → Seu serviço → Logs

---

**✨ Está pronto para fazer o deploy!** 

Dúvidas? Me avise! 🚀
