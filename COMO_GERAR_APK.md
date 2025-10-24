# 📱 Como Gerar o APK do Aplicativo

## Método 1: GitHub Actions (Automático) ⭐ RECOMENDADO

### Passo 1: Criar Repositório no GitHub

1. Acesse https://github.com e faça login (ou crie uma conta grátis)
2. Clique no botão **"+"** no canto superior direito
3. Selecione **"New repository"**
4. Preencha:
   - **Repository name**: `avaliacao-veiculos-app`
   - **Description**: `Aplicativo de Avaliação de Veículos Seminovos`
   - Marque **"Public"** (ou Private se preferir)
5. Clique em **"Create repository"**

### Passo 2: Fazer Upload do Código

**Opção A: Via Interface Web (Mais Fácil)**

1. Na página do repositório criado, clique em **"uploading an existing file"**
2. Arraste todos os arquivos da pasta `frontend-integrado` para a área de upload
3. Aguarde o upload completar
4. Role até o final e clique em **"Commit changes"**

**Opção B: Via Git (Se souber usar)**

```bash
cd frontend-integrado
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/avaliacao-veiculos-app.git
git push -u origin main
```

### Passo 3: Ativar GitHub Actions

1. No repositório, clique na aba **"Actions"**
2. Se aparecer uma mensagem, clique em **"I understand my workflows, go ahead and enable them"**
3. O build começará automaticamente!

### Passo 4: Baixar o APK

1. Aguarde o build terminar (5-10 minutos)
   - Você verá um ✅ verde quando estiver pronto
2. Clique no build concluído
3. Role até **"Artifacts"** no final da página
4. Clique em **"app-debug"** para baixar o APK
5. Extraia o arquivo ZIP baixado
6. Dentro estará o arquivo **`app-debug.apk`**

### Passo 5: Instalar no Celular

1. Transfira o arquivo `app-debug.apk` para seu celular
   - Pode enviar por WhatsApp, email, ou cabo USB
2. No celular, toque no arquivo APK
3. Se aparecer aviso de segurança:
   - Vá em **Configurações** → **Segurança**
   - Ative **"Fontes desconhecidas"** ou **"Instalar apps desconhecidos"**
4. Toque em **"Instalar"**
5. Pronto! O app estará instalado

---

## Método 2: Compilar Localmente no Computador

### Requisitos

- Android Studio instalado
- 8 GB de RAM
- 10 GB de espaço livre

### Passos

1. Abra o Android Studio
2. Selecione **"Open"** e escolha a pasta `frontend-integrado`
3. Aguarde o Gradle sincronizar (primeira vez demora)
4. No menu: **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
5. Aguarde a compilação (5-10 minutos)
6. Quando aparecer **"APK(s) generated successfully"**, clique em **"locate"**
7. O APK estará em: `app/build/outputs/apk/debug/app-debug.apk`
8. Copie para o celular e instale

---

## Método 3: Usar Serviço Online de Build

### AppCenter (Microsoft)

1. Acesse https://appcenter.ms
2. Crie uma conta grátis
3. Crie um novo app Android
4. Conecte com GitHub ou faça upload do código
5. Configure o build
6. Baixe o APK gerado

### Outras opções:
- **Bitrise** (https://bitrise.io)
- **CircleCI** (https://circleci.com)
- **Travis CI** (https://travis-ci.com)

---

## ⚠️ Observações Importantes

### Sobre Segurança

O APK gerado será **não-assinado** (debug), o que significa:

- ✅ Funciona perfeitamente para uso pessoal
- ✅ Pode instalar em qualquer dispositivo seu
- ❌ Não pode publicar na Google Play Store
- ❌ Android mostrará aviso de "app desconhecido"

### Para Publicar na Play Store

Se quiser publicar oficialmente, você precisará:

1. Criar uma chave de assinatura (keystore)
2. Assinar o APK com a chave
3. Gerar um APK de release (não debug)
4. Criar conta de desenvolvedor Google ($25 único)
5. Fazer upload na Play Console

---

## 🆘 Problemas Comuns

### "Build failed"
- Verifique se todos os arquivos foram enviados corretamente
- Tente novamente clicando em "Re-run all jobs"

### "Artifact not found"
- O build pode ter falhado
- Verifique os logs do Actions para ver o erro

### "App não instala no celular"
- Verifique se permitiu instalação de fontes desconhecidas
- Certifique-se de que o celular tem Android 7.0 ou superior

### "App fecha ao abrir"
- Pode ser incompatibilidade de versão
- Tente compilar novamente com API level diferente

---

## 📞 Precisa de Ajuda?

Se tiver dificuldades em qualquer etapa, me avise qual erro apareceu que eu te ajudo!

---

**Recomendação:** Use o **Método 1 (GitHub Actions)** - é o mais fácil e não precisa instalar nada no seu computador!

