# 🤖 Dependabot Configuration

Este projeto usa o **Dependabot** para manter as dependências sempre atualizadas automaticamente.

## 📋 Configuração

### 🗓️ Agendamento
- **Frequência**: Semanalmente (todas as segundas-feiras às 09:00, timezone de Lisboa)
- **Limite**: Máximo 10 PRs abertos simultaneamente
- **Auto-assign**: PRs são automaticamente atribuídos a `devTase`

### 📦 Dependências Monitorizadas

#### Maven Dependencies
- ✅ **Produção** (runtime dependencies)
- ✅ **Desenvolvimento** (test dependencies)
- ✅ **Build tools** (plugins Maven)

#### GitHub Actions
- ✅ **Workflows** (.github/workflows/*.yml)

## 🏷️ Organização por Grupos

As dependências são agrupadas logicamente:

- **`junit`**: org.junit.*, junit*
- **`mockito`**: org.mockito*
- **`guice`**: com.google.inject*
- **`database`**: mysql*, com.h2database*
- **`build-tools`**: org.jacoco*, com.diffplug.spotless*, org.apache.maven.plugins*

## 🔄 Auto-merge Policy

### ✅ **Merge Automático** (após CI passar):
- **Patch updates** (1.0.0 → 1.0.1) - Todas as dependências
- **Minor updates** (1.0.0 → 1.1.0) - Apenas para:
  - JUnit
  - Mockito  
  - JaCoCo
  - Spotless

### ⚠️ **Revisão Manual Obrigatória**:
- **Major updates** (1.0.0 → 2.0.0) - Todas as dependências
- **MySQL** versão 9.x+ (comentário no código indica "keep it in version 5")

## 🚦 Workflow de Aprovação

1. **Dependabot cria PR** → Labels automáticos + Assignment
2. **CI Pipeline executa** → Build + Testes + Spotless + PMD
3. **Auto-merge decision**:
   - ✅ **Patch/Minor seguro**: Merge automático
   - ⚠️ **Major**: Comentário + Revisão manual necessária

## 🏷️ Labels Automáticos

- `dependencies` - Todas as atualizações
- `java` - Dependências Maven
- `github-actions` - Actions workflows

## 📝 Commit Messages

- **Maven**: `deps: update dependency-name from x.x.x to y.y.y`
- **GitHub Actions**: `ci: update action-name from x.x.x to y.y.y`

## 🛡️ Proteções

### Dependências Ignoradas:
- **mysql:mysql-connector-java v9.x**: Mantém compatibilidade com versão 5

### Verificações Obrigatórias:
- ✅ CI Pipeline deve passar (build + testes)
- ✅ Spotless formatting check
- ✅ PMD static analysis

## 🔧 Gestão Manual

### Forçar Verificação Imediata
```bash
# Via GitHub CLI
gh api repos/devTase/words-crush-game/dependabot/alerts
```

### Desativar Temporariamente  
Editar `.github/dependabot.yml` e comentar a secção desejada.

### Merge Manual de Major Updates
```bash
# Verificar changes
gh pr view <PR_NUMBER> --json body
# Após revisão
gh pr merge <PR_NUMBER> --squash
```

## 🎯 Benefícios

- 🔄 **Dependências sempre atualizadas**
- 🛡️ **Patches de segurança automáticos**
- ⚡ **Zero overhead** para updates menores
- 🎯 **Foco manual** apenas em mudanças importantes
- 📊 **Visibilidade completa** do que está a ser atualizado
