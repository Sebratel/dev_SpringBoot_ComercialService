# dev_SpringBoot_ComercialService

Vendas e contratos (relatórios do ERP, somente leitura).

Parte da migração do BFF para API Gateway (`Sebratel/dev_SpringBoot_APIGateway`, `specs/API_GATEWAY.md`).
Serviço **comercial-service**, porta **8103**, atrás do `dev_SpringBoot_Gateway`.

> **Estado: esqueleto.** Sobe, expõe `/actuator/health` (público; o resto exige JWT) e tem build, Checkstyle e JaCoCo ativos, mas **a lógica ainda está no
> monólito**. As rotas abaixo são o contrato que este serviço precisa atender, idêntico ao do BFF atual (`contract/routes.json`).

## Rotas que este serviço deve atender (45)

| Método | Rota | Acesso |
|---|---|---|
| `GET` | `/api/v1/acquisitions/recover-acquisition-orders` | autenticada |
| `GET` | `/api/v1/acquisitions/recuperar-pedidos-de-aquisicao` | autenticada |
| `GET` | `/api/v1/apoio-semanal/vendedor` | autenticada |
| `GET` | `/api/v1/aquisicoes/recover-acquisition-orders` | autenticada |
| `GET` | `/api/v1/aquisicoes/recuperar-pedidos-de-aquisicao` | autenticada |
| `GET` | `/api/v1/contract-activations/report` | autenticada |
| `GET` | `/api/v1/contract-payments/first-activation` | autenticada |
| `GET` | `/api/v1/contracts/ativacao-pendente-fatura` | autenticada |
| `GET` | `/api/v1/contracts/blocked` | autenticada |
| `GET` | `/api/v1/contracts/bloqueados` | autenticada |
| `GET` | `/api/v1/contracts/pending-activation-invoice` | autenticada |
| `GET` | `/api/v1/contracts/sem-fatura` | autenticada |
| `GET` | `/api/v1/contracts/without-invoice` | autenticada |
| `GET` | `/api/v1/contrato-ativacao-fatura/ativacao-pendente-fatura` | autenticada |
| `GET` | `/api/v1/contrato-ativacao-fatura/pending-activation-invoice` | autenticada |
| `GET` | `/api/v1/contrato-bloqueado/blocked` | autenticada |
| `GET` | `/api/v1/contrato-bloqueado/bloqueados` | autenticada |
| `GET` | `/api/v1/contrato-sem-fatura/sem-fatura` | autenticada |
| `GET` | `/api/v1/contrato-sem-fatura/without-invoice` | autenticada |
| `GET` | `/api/v1/contratos/ativacao-pendente-fatura` | autenticada |
| `GET` | `/api/v1/contratos/blocked` | autenticada |
| `GET` | `/api/v1/contratos/bloqueados` | autenticada |
| `GET` | `/api/v1/contratos/pending-activation-invoice` | autenticada |
| `GET` | `/api/v1/contratos/report` | autenticada |
| `GET` | `/api/v1/contratos/sem-fatura` | autenticada |
| `GET` | `/api/v1/contratos/without-invoice` | autenticada |
| `GET` | `/api/v1/primeiro-pagante-mensal/first-monthly-payer` | autenticada |
| `GET` | `/api/v1/primeiro-pagante-mensal/primeiro-pagante-mensal` | autenticada |
| `GET` | `/api/v1/relatorio-cliente-nome-duplicado/clientes-nomes-duplicados` | autenticada |
| `GET` | `/api/v1/relatorio-cliente-nome-duplicado/duplicate-client-names` | autenticada |
| `GET` | `/api/v1/relatorios/clientes-nomes-duplicados` | autenticada |
| `GET` | `/api/v1/relatorios/duplicate-client-names` | autenticada |
| `GET` | `/api/v1/relatorios/first-monthly-payer` | autenticada |
| `GET` | `/api/v1/relatorios/primeiro-pagante-mensal` | autenticada |
| `GET` | `/api/v1/reports/clientes-nomes-duplicados` | autenticada |
| `GET` | `/api/v1/reports/duplicate-client-names` | autenticada |
| `GET` | `/api/v1/reports/first-monthly-payer` | autenticada |
| `GET` | `/api/v1/reports/primeiro-pagante-mensal` | autenticada |
| `GET` | `/api/v1/sellers/active` | autenticada |
| `GET` | `/api/v1/sellers/ativos` | autenticada |
| `GET` | `/api/v1/sellers/vendedores-ativos` | autenticada |
| `GET` | `/api/v1/vendedores/active` | autenticada |
| `GET` | `/api/v1/vendedores/ativos` | autenticada |
| `GET` | `/api/v1/vendedores/vendedores-ativos` | autenticada |
| `GET` | `/api/v1/weekly-reports/vendedor` | autenticada |

## Desenvolvimento

O `sebratel-java-parent` vem do GitHub Packages (o Maven exige token `read:packages` até para pacotes públicos). Antes do primeiro build, crie no `~/.m2/settings.xml` um servidor
`github` com um token `read:packages`:

```xml
<settings><servers><server>
  <id>github</id><username>SEU_USUARIO</username><password>SEU_TOKEN</password>
</server></servers></settings>
```

```bash
./mvnw verify          # testes + Checkstyle + JaCoCo (cobertura mínima 0.50, herdada do parent)
./mvnw spring-boot:run
```

Variáveis de ambiente: ver `.env.example` (nunca commite o `.env`).

## Regras do repositório
- `main` só recebe PR, com 1 aprovação e o check `ci-ok` verde. Commits no padrão Conventional Commits.
- Dependências compartilhadas (`common-web`, `common-security`, `common-elleven`) vêm de `dev_Maven_Commons`
  e entram quando houver código a compartilhar.
- Remover ou renomear rota é mudança incompatível: o contrato é o manifesto do monólito (`docs/contract/manifest.json`).

## Deploy em staging (Portainer)

Stack criada **a partir do Git**: repositório `https://github.com/Sebratel/dev_SpringBoot_ComercialService`, branch `staging`,
arquivo `docker-compose-stage.yml`. O container se chama `comercial-service-stage` e fica na rede externa `proxy`.
Sem porta publicada no host: só o gateway, na rede `proxy`, alcança este serviço.

A imagem `ghcr.io/sebratel/comercial-service:staging` é construída pelo GitHub Actions (`.github/workflows/staging-image.yml`, a cada push em `staging`) a partir
do `Dockerfile` (multi-stage, Temurin 21, usuário não-root) e publicada no GHCR; o Portainer só a baixa. Construir a imagem dentro do host
passa de 90 s, e o proxy do stage derruba a requisição de deploy. O `sebratel-java-parent` é construído da fonte pública na própria imagem, sem token.

O branch `staging` recebe o código mais atualizado: `main` → `staging` antes de cada deploy.

## Estado da migração

O código dos endpoints deste serviço **já foi movido** do BFF original (`dev_SpringBoot_APIGateway`) para cá: controllers, services, repositórios e testes, com a mesma resposta (o contrato fica em `contract/routes.json`). O gateway roteia estas rotas para este serviço.

Variáveis de ambiente de banco/integração: ver `.env.example`. No stage elas são definidas na stack do Portainer.
