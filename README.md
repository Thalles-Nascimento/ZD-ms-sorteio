# Zenix Draw · Sorteio (`ZD-ms-sorteio`)

Microsserviço responsável por sortear jogadores em times para "peladas" (partidas informais de futebol).

Dado um conjunto de jogadores e a quantidade de times desejada, o serviço distribui os jogadores aleatoriamente entre os times e devolve, além dos times formados, a lista de jogadores que sobraram como reserva.

## Sobre o projeto

Este repositório (`ZD-ms-sorteio` no GitHub) faz parte do ecossistema **Zenix Draw**. Hoje, no GitHub, ele convive com apenas mais um repositório publicado:

- [`ZD-api-gateway`](https://github.com/Thalles-Nascimento/ZD-api-gateway) — API Gateway (Spring Cloud Gateway).

Há também um serviço de service discovery (Netflix Eureka) em desenvolvimento localmente na máquina do autor, mas ele ainda **não foi versionado/publicado no GitHub**, então não é considerado parte do ecossistema disponível publicamente por enquanto.

Hoje, o `ms-sorteio` funciona de forma **standalone**: ele expõe sua API REST diretamente e pode ser consumido sem depender do Gateway.

## Status do projeto / arquitetura atual

Este projeto ainda está em desenvolvimento, então a integração com o Gateway (e, futuramente, com o Eureka) ainda não está totalmente amadurecida. Vale deixar claro o estado atual:

- **Eureka**: existe um serviço de descoberta em desenvolvimento local, mas ele ainda não foi publicado no GitHub. O `ms-sorteio` **não possui** o client do Eureka (`spring-cloud-starter-netflix-eureka-client`) e, portanto, **não se registra** em nenhum servidor de descoberta hoje.
- **Gateway**: o `ZD-api-gateway` já tem uma rota configurada apontando para este serviço:

  ```yaml
  routes:
    - id: ms-sorteio
      uri: http://localhost:8081/
      predicates:
        - Path=/api/v1/sorteio/**
  ```

  Só que esse roteamento é feito por **URI estática** (`http://localhost:8081/`), e não por descoberta de serviço via Eureka (ex.: `lb://ms-sorteio`). Ou seja, o Gateway consegue encaminhar chamadas para o `ms-sorteio`, mas de forma fixa/manual, não dinâmica.

Em resumo: a integração real entre o `ms-sorteio` e o Gateway (e, no futuro, o registro via Eureka) ainda é um próximo passo, não algo já consolidado.

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot 4.0.7** (`spring-boot-starter-webmvc`)
- **Log4j2 + LMAX Disruptor** — logging assíncrono
- **springdoc-openapi** — documentação/UI da API (Swagger)
- **Lombok**
- **Maven** (com Maven Wrapper)
- **JUnit 5 + AssertJ** — testes (via `spring-boot-starter-webmvc-test`)

## Regras de negócio do sorteio

O algoritmo, implementado em `SorteioService.sortearTimes`, segue estes passos:

1. Calcula quantos jogadores cabem por time: `totalDeJogadores / numeroTimes` (divisão inteira).
2. Para cada time, sorteia aleatoriamente (sem reposição) os jogadores necessários, removendo cada jogador sorteado da lista original.
3. Ordena os jogadores de cada time formado pela posição (`Posicao`), seguindo a ordem: `GOLEIRO → LATERAL → ZAGUEIRO → MEIO_CAMPO → ATACANTE`.
4. Jogadores que não couberem em nenhum time por conta da divisão inteira permanecem como **reservas** na resposta.

**Limitações conhecidas (projeto em desenvolvimento):**

- O campo `forca` do jogador já existe no modelo, mas **ainda não é usado** para balancear os times — o sorteio é puramente aleatório, sem considerar nível de habilidade.
- Não há validação de entrada: enviar `numeroTimes` igual a `0` causa `ArithmeticException`, e não há checagem de lista de jogadores vazia ou nula.

## API

### `POST /api/v1/sorteio`

Recebe a lista de jogadores e o número de times desejado, e devolve os times sorteados.

**Request** (`ListaDTO`):

```json
{
  "jogadores": [
    { "nome": "Thalles", "posicao": "ATACANTE", "forca": 8.0 },
    { "nome": "Gustavo", "posicao": "ZAGUEIRO", "forca": 7.5 },
    { "nome": "Jorlan", "posicao": "GOLEIRO", "forca": 9.0 },
    { "nome": "Mattheus", "posicao": "LATERAL", "forca": 6.0 }
  ],
  "numeroTimes": 2
}
```

**Response** — `201 Created` (`ListaDTOResponse`):

```json
{
  "times": {
    "time1": [
      { "nome": "Jorlan", "posicao": "GOLEIRO", "forca": 9.0 },
      { "nome": "Thalles", "posicao": "ATACANTE", "forca": 8.0 }
    ],
    "time2": [
      { "nome": "Mattheus", "posicao": "LATERAL", "forca": 6.0 },
      { "nome": "Gustavo", "posicao": "ZAGUEIRO", "forca": 7.5 }
    ]
  },
  "reservas": [],
  "jogadoresPorTime": 2
}
```

> Posições válidas para o campo `posicao`: `GOLEIRO`, `LATERAL`, `ZAGUEIRO`, `MEIO_CAMPO`, `ATACANTE`.

Com o serviço em execução, a documentação interativa (Swagger UI) fica disponível em:

- `http://localhost:8081/swagger-ui/index.html`
- `http://localhost:8081/v3/api-docs`

## Como executar

Pré-requisito: **Java 21**.

```bash
./mvnw spring-boot:run
```

O serviço sobe na porta **8081**, com o endpoint disponível em `http://localhost:8081/api/v1/sorteio`.

### Executando via Gateway (opcional)

Como descrito em [Status do projeto](#status-do-projeto--arquitetura-atual), também é possível subir o [`ZD-api-gateway`](https://github.com/Thalles-Nascimento/ZD-api-gateway) (porta `8080`) e acessar o serviço através dele em `http://localhost:8080/api/v1/sorteio`, já que existe uma rota estática configurada para isso.

## Testes

O projeto conta com testes de contexto e testes unitários para a regra de sorteio.

| Classe | Tipo | Método | O que verifica |
|---|---|---|---|
| `ZenixDrawMsSorteioApplicationTests` | Contexto (Spring Boot Test) | `contextLoads` | O contexto da aplicação sobe sem erros |
| `SorteioServiceTest` | Unitário (JUnit 5 + AssertJ) | `deveSortearDoisTimesSemReservas` | Com 4 jogadores e 2 times, são formados exatamente 2 times e não sobra nenhum jogador como reserva |
| `SorteioServiceTest` | Unitário (JUnit 5 + AssertJ) | `listaDeJogadoresDeveEstarOrdenadaPorPosicao` | Cada time formado está ordenado pela posição dos jogadores (`Posicao`) |
| `SorteioServiceTest` | Unitário (JUnit 5 + AssertJ) | `naoDeveConterNulos` | A resposta e as listas de cada time não são nulas nem vazias |

Os testes unitários de `SorteioServiceTest` instanciam o `SorteioService` diretamente (`new SorteioService()`), sem subir o contexto do Spring, já que o serviço não possui dependências externas a serem mockadas.

Para rodar os testes:

```bash
./mvnw test
```

## Estrutura do projeto

```
src/main/java/com/br/thallesnascimento/zenix_draw_ms_sorteio/
├── ZenixDrawMsSorteioApplication.java
├── controllers/
│   └── SorteioController.java       # POST /api/v1/sorteio
├── services/
│   └── SorteioService.java          # regra de negócio do sorteio
└── models/
    ├── dtos/
    │   ├── ListaDTO.java
    │   └── ListaDTOResponse.java
    ├── entities/
    │   └── Jogador.java
    └── enums/
        └── Posicao.java
```

## Roadmap / Próximos passos

- Registrar o `ms-sorteio` como client do Eureka (`spring-cloud-starter-netflix-eureka-client`).
- Migrar a rota do Gateway de URI estática para descoberta dinâmica via Eureka (`lb://ms-sorteio`).
- Adicionar validações de entrada (`numeroTimes` inválido, lista de jogadores vazia/nula).
- Considerar o uso do campo `forca` para balancear os times, além de apenas sortear aleatoriamente.
- Persistência (histórico de sorteios) e containerização (Docker).
