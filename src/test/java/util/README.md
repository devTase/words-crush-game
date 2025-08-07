# Test Utilities

Este diretório contém classes helper para reduzir duplicação nos testes e facilitar a criação de objetos de teste com configuração controlável.

## Classes Disponíveis

### 1. DummySocket
**Propósito**: Socket testável com comportamento controlável para simular condições de erro.

**Características**:
- Controlo sobre se deve lançar exceção no `close()`
- Estado interno `isClosed()` observável
- Configuração flexível via construtores e setters

**Uso**:
```java
// Socket normal
DummySocket socket = new DummySocket();
socket.close(); // Funciona normalmente
assertTrue(socket.isClosed());

// Socket que falha ao fechar
DummySocket failingSocket = new DummySocket(true);
assertThrows(IOException.class, () -> failingSocket.close());

// Socket com exceção customizada
DummySocket customSocket = new DummySocket(new IOException("Custom error"));
```

### 2. UserBuilder
**Propósito**: Builder pattern para criar objetos User com defaults sensatos e configuração fluente.

**Características**:
- Defaults automáticos para todos os campos obrigatórios
- Mocks automáticos para dependências
- Factory methods para cenários comuns
- API fluente e legível

**Uso**:
```java
// Player básico com defaults
Player player = UserBuilder.defaultPlayer().buildPlayer();

// Player customizado
Player custom = UserBuilder.defaultPlayer()
    .withUserName("customUser")
    .withScore(100)
    .withLives(1)
    .withReady(true)
    .buildPlayer();

// Cenários pré-definidos
Player kicked = UserBuilder.kickedPlayer().buildPlayer();
Player ready = UserBuilder.readyPlayer().buildPlayer();
Admin admin = UserBuilder.defaultAdmin().buildAdmin();
```

### 3. StageStub
**Propósito**: Stub implementation de Stage com comportamento controlável para testes.

**Características**:
- Implementa todos os métodos abstratos da interface
- Controlo sobre lançamento de exceções
- Factory methods para diferentes tipos de stage
- Estado interno configurável

**Uso**:
```java
// Stages para diferentes cenários
Stage waiting = StageStub.waitingRoomStub();
Stage game = StageStub.gameRoomStub();
Stage full = StageStub.fullStage(); // Com 5 users pré-criados

// Stage que lança exceções
Stage throwing = StageStub.throwingStage(new RuntimeException("Test error"));

// Controlo manual
StageStub custom = new StageStub("CustomStage");
custom.setShouldThrowException(true);
```

### 4. MockProjectProperties
**Propósito**: Mock injetável do ProjectProperties sem necessidade de static mocking.

**Características**:
- Map injetável de propriedades
- Defaults sensatos para testes
- Builder pattern para configuração complexa
- Factory methods para cenários específicos

**Uso**:
```java
// Properties com defaults
MockProjectProperties props = MockProjectProperties.withDefaults();
assertEquals("testAdmin", props.getProperty("admin.name"));

// Scores customizados
MockProjectProperties scores = MockProjectProperties.withScores(10, 50, 100);
assertEquals("50", scores.getProperty("server.grid.score.1"));

// Builder fluente
MockProjectProperties complex = MockProjectProperties.builder()
    .withAdminCredentials("admin", "password")
    .withGridRows(10)
    .withScore(0, 15)
    .build();

// Properties vazias
MockProjectProperties empty = MockProjectProperties.empty();
```

## Vantagens

### Antes vs Depois

**Antes (setup verboso)**:
```java
@Mock private ClientDispatch mockClientDispatch;
@Mock private Socket mockSocket;
@Mock private Stage mockStage;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    when(mockSocket.close()).thenThrow(new IOException("Error"));
}

@Test
void testPlayerKick() {
    Player player = new Player(1, "testUser", 0, 3, false, 
                              mockClientDispatch, mockSocket, mockStage, false, false);
    // teste...
}
```

**Depois (setup limpo)**:
```java
@Test
void testPlayerKick() {
    DummySocket socket = new DummySocket(new IOException("Error"));
    Player player = UserBuilder.defaultPlayer()
                              .withSocket(socket)
                              .buildPlayer();
    // teste...
}
```

## Integração com Testes Existentes

Para usar estas utilities nos testes existentes:

1. **Substitui mocks complexos** pelos stubs/builders
2. **Reduz código de setup** nos `@BeforeEach`
3. **Melhora legibilidade** dos testes
4. **Facilita manutenção** com mudanças centralizadas

## Exemplo Completo

Consulta `UtilityClassesExampleTest.java` para ver exemplos completos de uso de todas as utilities em conjunto.
