# Linketinder

## Tecnologias Utilizadas
- Groovy 5.0
- JVM 25

## Build e Execução
```bash
./gradlew build
./gradlew run --console=plain
```

## Estrutura do Projeto
```
src/
├── Main.groovy
├── model/    # Entidades do domínio
└── service/  # Lógica de negócio
```

## Frontend

### Tecnologias Utilizadas
- Chart.js (CDN)

### Features TypeScript Utilizadas
- Interfaces para tipagem de dados (Candidato, Empresa, Vaga)
- Namespace para organização das funções de armazenamento
- Generics com Omit para operações de criação
- Type assertions para manipulação do DOM
- Módulos ES6 para importação/exportação

### Como Executar
1. Compile o TypeScript com `tsc`
2. Abra o arquivo `frontend/index.html` com um servidor local (ex: Live Server)
3. Os dados são salvos automaticamente no localStorage

Feito por Ângelo.