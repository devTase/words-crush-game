# Code Formatting com Spotless

Este projeto usa o [Spotless](https://github.com/diffplug/spotless) com PalantirJavaFormat para manter a formatação consistente do código Java.

## 📋 Configuração

O projeto está configurado com:
- **PalantirJavaFormat 2.39.0** com estilo PALANTIR
- **Static imports primeiro**, seguidos de linha vazia
- **Remoção automática de imports não utilizados**
- **Formatação de anotações** e **quebra de linha no final dos ficheiros**
- **Line endings UNIX** para consistência entre sistemas

## 🚀 Comandos Disponíveis

### Verificar formatação
```bash
mvn spotless:check
```

### Aplicar formatação
```bash
mvn spotless:apply
```

### Script helper (mais rápido)
```bash
./format-code.sh
```

## 🔧 Pre-commit Hook

O projeto tem um **pre-commit hook** que:

1. ✅ **Verifica automaticamente** a formatação antes de cada commit
2. ❌ **Aborta o commit** se houver problemas de formatação
3. 🔧 **Aplica as correções automaticamente** com `mvn spotless:apply`
4. 💡 **Pede para refazer o commit** após corrigir

### Como funciona:
```bash
git commit -m "minha mensagem"
# → Hook executa mvn spotless:check
# → Se falhar: aplica correções e aborta
# → Se passar: commit é efetuado normalmente
```

## 🎯 Benefícios

- **Zero discussões** sobre estilo de código
- **Formatação consistente** em toda a base de código  
- **Integração automática** no processo de commit
- **Detecção precoce** de problemas de formatação
- **Aplicação automática** das correções

## 📝 Regras Aplicadas

- **Import order**: Static imports primeiro, depois linha vazia
- **Remove unused imports**: Limpa imports desnecessários
- **Format annotations**: Formatação consistente de anotações
- **End with newline**: Todos os ficheiros terminam com nova linha
- **PalantirJavaFormat**: Estilo consistente e legível

## 🔍 Troubleshooting

Se o commit hook não funcionar:
```bash
# Verificar configuração
git config core.hooksPath

# Deve retornar: .git_hooks
# Se não, executar:
git config core.hooksPath .git_hooks
```

Se precisar de aplicar formatação manualmente:
```bash
./format-code.sh
# ou
mvn spotless:apply
```
