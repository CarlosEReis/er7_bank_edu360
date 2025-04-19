
# 💳 Projeto Final - ER7Bank API

Este projeto foi desenvolvido como parte do **projeto final do Bootcamp Java Developer da Educ360**. A proposta é construir uma **API RESTful completa para um Banco Digital**, simulando operações financeiras reais com autenticação, manipulação de contas, cartões e seguros.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security + JWT (opcional)
- Hibernate
- PostgreSQL
- Maven


## 📦 Funcionalidades da API

### 👤 Cliente
- `POST /clientes` - Criar novo cliente
- `GET /clientes/{id}` - Obter detalhes do cliente
- `PUT /clientes/{id}` - Atualizar informações
- `DELETE /clientes/{id}` - Remover cliente
- `GET /clientes` - Listar todos os clientes

### 🏦 Conta
- `POST /contas` - Criar nova conta
- `GET /contas/{id}` - Detalhes da conta
- `POST /contas/{id}/transferencia` - Transferência entre contas
- `GET /contas/{id}/saldo` - Consultar saldo
- `POST /contas/{id}/pix` - Pagamento via Pix
- `POST /contas/{id}/deposito` - Realizar depósito
- `POST /contas/{id}/saque` - Realizar saque
- `PUT /contas/{id}/manutencao` - Aplicar taxa de manutenção (corrente)
- `PUT /contas/{id}/rendimentos` - Aplicar rendimento (poupança)

### 💳 Cartão
- `POST /cartoes` - Emitir novo cartão
- `GET /cartoes/{id}` - Detalhes do cartão
- `POST /cartoes/{id}/pagamento` - Pagamento com o cartão
- `PUT /cartoes/{id}/limite` - Alterar limite
- `PUT /cartoes/{id}/status` - Ativar/Desativar
- `PUT /cartoes/{id}/senha` - Alterar senha
- `GET /cartoes/{id}/fatura` - Consultar fatura
- `POST /cartoes/{id}/fatura/pagamento` - Pagar fatura
- `PUT /cartoes/{id}/limite-diario` - Alterar limite diário (débito)

### 📦 Seguro (opcional)
- `POST /seguros` - Contratar seguro
- `GET /seguros/{id}` - Detalhes da apólice
- `GET /seguros` - Listar seguros
- `PUT /seguros/{id}/cancelar` - Cancelar apólice

### 🔐 Autenticação e Segurança (opcional)
- Autenticação com **JWT**
- Perfis com roles `ADMIN` e `CLIENTE`

## 🌐 Integrações Externas (opcionais)

- Consulta de CPF na Receita Federal (simulada ou externa)
- Cotação de moedas e conversão com API de câmbio (Ex: Banco Central)
