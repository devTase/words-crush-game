# 🎮 Words Crush Game - Available Commands

## 📋 **Lista de Comandos Disponíveis**

Após fazer login com `admin/admin` ou `player/player`, você pode usar os seguintes comandos no chat:

### 🔧 **Comandos Básicos**

| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| `/help` | Mostra lista de comandos disponíveis | `/help` |
| `/list` | Mostra lista de jogadores conectados | `/list` |
| `/ready` | Define-se como pronto para jogar | `/ready` |

### 👥 **Comandos de Comunicação**

| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| `/pm <jogador> <mensagem>` | Envia mensagem privada | `/pm player Hello!` |

### ⚙️ **Comandos de Admin (apenas ROOT/ADMIN)**

| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| `/kick <jogador>` | Remove jogador do jogo | `/kick player` |
| `/start` | Inicia o jogo | `/start` |
| `/start -a` | Define todos os jogadores como prontos | `/start -a` |

### 💬 **Chat Livre**

- **Mensagens sem `/`** - Enviam mensagem para todos os jogadores no chat
- Exemplo: `Hello everyone!`

---

## 🎯 **Como Usar os Comandos**

### **1. Fazer Login**
```
1. Execute o cliente: mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
2. Conecte-se a: 127.0.0.1:8001
3. Escolha "Login" 
4. Use: admin/admin ou player/player
```

### **2. Testar Comandos Básicos**
```
/help          # Ver todos os comandos
/list          # Ver quem está online
/ready         # Marcar-se como pronto
```

### **3. Comandos de Admin**
```
# Como admin, você pode:
/start -a      # Marcar todos como prontos
/start         # Iniciar o jogo
/kick player   # Remover um jogador
```

### **4. Chat e Mensagens Privadas**
```
Hello everyone!           # Mensagem pública
/pm player Olá como vai?  # Mensagem privada
```

---

## 🔍 **Troubleshooting**

### **Comandos não funcionam?**

1. **Verifique se está logado corretamente:**
   - Admin: `admin/admin`
   - Player: `player/player`

2. **Certifique-se de usar `/` antes dos comandos:**
   - ✅ Correto: `/help`
   - ❌ Errado: `help`

3. **Verifique se há outros jogadores:**
   - Alguns comandos precisam de múltiplos jogadores
   - Use `/list` para ver quem está conectado

4. **Comandos de admin só funcionam para ADMIN/ROOT:**
   - `/kick`, `/start`, `/start -a` são apenas para admins
   - Players podem usar `/help`, `/list`, `/ready`, `/pm`

### **Mensagens de Erro Comuns**

- `"[INFO] Invalid Command!"` - Comando não existe ou digitado errado
- `"Blank Message not valid!"` - Mensagem vazia
- `"You don't have admin rights"` - Tentativa de usar comando admin como player

---

## 🎮 **Fluxo do Jogo**

1. **Waiting Room** - Jogadores se conectam e ficam no chat
2. **Ready Phase** - Jogadores usam `/ready` ou admin usa `/start -a`  
3. **Game Starts** - Quando todos estão prontos, o jogo inicia
4. **Word Game** - Jogo de palavras propriamente dito

---

## 🧪 **Exemplo de Sessão**

```bash
# Terminal 1 - Servidor
mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.DemoProgram"

# Terminal 2 - Cliente Admin
mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
# Login: admin/admin
# Digite: /help

# Terminal 3 - Cliente Player  
mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
# Login: player/player  
# Digite: /ready

# No cliente admin:
# Digite: /start
```

---

**🎉 Divirta-se jogando Words Crush Game!**
