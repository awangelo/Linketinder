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
src/main/groovy/com/awangelo/
├── Main.groovy
├── controller/   # Controllers REST (Candidato, Empresa, Vaga)
├── dao/          # Data Access Objects (PostgreSQL)
├── db/           # Gerenciamento de conexão com BD
├── model/        # Entidades do domínio (Candidato, Empresa, Vaga, Competencia)
├── router/       # Configuração de rotas HTTP
├── service/      # Lógica de negócio
└── util/         # Utilitários JSON e Validações
```

### Endpoints
  - `GET/POST /candidatos` - Listar e criar candidatos
  - `GET /candidatos/{id}` - Buscar candidato por ID
  - `GET/POST /empresas` - Listar e criar empresas
  - `GET /empresas/{id}` - Buscar empresa por ID
  - `GET/POST /vagas` - Listar e criar vagas
  - `GET /vagas/{id}` - Buscar vaga por ID

### Validações Implementadas
- **Email**: Formato válido (contém @ e .)
- **CPF**: 11 dígitos numéricos
- **CNPJ**: 14 dígitos numéricos
- **CEP**: 8 dígitos numéricos
- **Senha**: Mínimo 6 caracteres (não retornada nas respostas)
- **Idade**: Mínimo 18 anos
- **Competências**: Válidas do enum
- **Empresa (Vaga)**: Empresa deve existir ao criar vaga

## Frontend

### Tecnologias Utilizadas
- Chart.js (CDN)

### Features TypeScript Utilizadas
- Interfaces para tipagem de dados (Candidato, Empresa, Vaga, CurrentUser)
- Namespace para organização das funções de armazenamento
- Generics com Omit para operações de criação
- Type assertions para manipulação do DOM
- Módulos ES6 para importação/exportação
- Validações utilizando regex nativo do TypeScript

### Como Executar
1. Compile o TypeScript com `tsc`
2. Abra o arquivo `frontend/index.html` com um servidor local (ex: Live Server)
3. Os dados são salvos automaticamente no localStorage

## Banco de Dados

![diagrama](db/diagrama.png)

### Tecnologias Utilizadas
- PostgreSQL
- Docker (Compose)

### Como Executar
```bash
cd db
docker-compose up -d
docker exec -it linketinder_postgres psql -U admin -d linketinder # Testar queries
```

#### Logica de Match
A tabela `curtida` possui uma coluna `origem_curtida` que permite apenas os valores `'CANDIDATO'` ou `'EMPRESA'`.
- Quando um candidato curte uma vaga, um registro é inserido com `origem_curtida = 'CANDIDATO'`.
- Quando a empresa curte um candidato para uma vaga específica, um novo registro é inserido com `origem_curtida = 'EMPRESA'`.
- O match ocorre quando existem dois registros na tabela `curtida` com o mesmo `candidato_id` e `vaga_id`, mas com origens opostas (Ex: `(1, 1, 'CANDIDATO'), (1, 1, 'EMPRESA')`).

Feito por Ângelo.