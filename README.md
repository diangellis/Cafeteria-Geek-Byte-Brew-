# ☕ Byte & Brew — Sistema de Cafeteria

Sistema de console desenvolvido em **Java** para gerenciamento de uma cafeteria temática. O projeto foi desenvolvido em equipe como trabalho final, com foco na aplicação prática de conceitos de **Programação Orientada a Objetos (POO)**, incluindo herança, abstração, interfaces, polimorfismo, Generics, exceções customizadas e padrões de projeto.

---

## 📌 Sobre o projeto

O **Byte & Brew** simula o funcionamento de uma cafeteria, permitindo gerenciar:

* Produtos;
* Clientes;
* Estoque;
* Vendas;
* Promoções;
* Sistema de fidelidade baseado em XP.

O sistema possui um menu interativo executado diretamente pelo terminal.

---

## 👨‍💻 Minha contribuição

Fui responsável principalmente pela **modelagem e implementação das entidades de Produto e Cliente**, incluindo suas respectivas hierarquias.

Também participei da implementação da lógica de persistência em memória utilizando **Generics**, por meio da classe `RepositorioGenerico<T>`, permitindo reutilizar as operações de CRUD para diferentes entidades.

Minhas principais responsabilidades foram:

* Modelagem da hierarquia de produtos;
* Implementação de `Product`, `Bebida` e `Comida`;
* Modelagem da hierarquia de clientes;
* Implementação de `Cliente`, `ClienteStandard` e `ClienteVIP`;
* Implementação e validação do controle de estoque;
* Desenvolvimento do sistema de XP dos clientes;
* Implementação de exceções relacionadas às regras de negócio;
* Desenvolvimento do `RepositorioGenerico<T>`;
* Integração dos módulos desenvolvidos com o restante do sistema;
* Colaboração utilizando Git/GitHub.

---

## 🏗️ Modelagem

### 🥤 Produtos

A classe abstrata `Product` implementa a interface `Entidade` e serve como base para os diferentes tipos de produtos.

```text
Product
├── Bebida
│   ├── Cafeína
│   └── Tamanho
│
└── Comida
    ├── Tempo de preparo
    ├── Vegano
    └── Contém glúten
```

Todos os produtos possuem controle de estoque, incluindo operações para:

* Adicionar estoque;
* Reduzir estoque;
* Validar quantidade disponível;
* Lançar `EstoqueInsuficienteException` quando necessário.

---

### 👤 Clientes

A classe abstrata `Cliente` também implementa `Entidade` e possui duas especializações:

```text
Cliente
├── ClienteStandard
│   └── 1 XP por real gasto
│
└── ClienteVIP
    ├── 2 XP por real gasto
    └── Pode utilizar XP para pagar pedidos
```

O sistema utiliza polimorfismo para aplicar diferentes regras de fidelidade de acordo com o tipo de cliente.

---

### 🛒 Vendas

A classe `Venda` representa uma venda realizada na cafeteria e agrega uma lista de `ItemPedido`.

Cada `ItemPedido` associa:

* Produto;
* Quantidade;
* Preço aplicado.

A classe `Venda` também utiliza **sobrecarga de métodos** para permitir a adição de produtos com ou sem quantidade explicitamente informada.

---

## 🧩 Conceitos e padrões aplicados

### Generics

Foi desenvolvido um repositório genérico:

```java
RepositorioGenerico<T extends Entidade>
```

Ele centraliza operações de:

* Cadastro;
* Edição;
* Exclusão;
* Listagem.

Dessa forma, a mesma estrutura pode ser reutilizada por diferentes entidades do sistema.

---

### Strategy

Foi utilizada a interface `Promocional` para definir regras de promoção.

Uma das implementações é:

```text
Promocional
└── DescontoDiaGeek
```

A estratégia permite adicionar novas regras de desconto sem precisar modificar diretamente a lógica principal da classe `Venda`.

---

### Herança e Polimorfismo

A aplicação utiliza hierarquias de classes para representar diferentes tipos de produtos e clientes.

Exemplos:

```text
Product → Bebida / Comida

Cliente → ClienteStandard / ClienteVIP
```

O polimorfismo permite que comportamentos específicos sejam executados de acordo com o tipo concreto do objeto.

---

### Exceções customizadas

Foram criadas exceções específicas para representar regras de negócio:

* `EstoqueInsuficienteException`
* `PontosInsuficientesException`
* `DadoVazioException`

Isso permite tratar situações inválidas de maneira mais clara e específica.

---

## ⚙️ Funcionalidades

### Produtos

* Cadastro de produtos;
* Edição de produtos;
* Remoção de produtos;
* Listagem de produtos;
* Controle de estoque;
* Validação de estoque.

### Clientes

* Cadastro de clientes;
* Edição de clientes;
* Remoção de clientes;
* Listagem de clientes;
* Acúmulo de XP;
* Diferenciação entre clientes Standard e VIP;
* Utilização de XP para pagamento por clientes VIP.

### Vendas

* Seleção de produtos;
* Definição de quantidade;
* Identificação opcional do cliente;
* Aplicação de promoções;
* Cálculo do valor da venda;
* Acúmulo de XP;
* Pagamento utilizando XP para clientes VIP.

---

## 🛠️ Tecnologias e conceitos

* **Java**
* Programação Orientada a Objetos
* Herança
* Abstração
* Encapsulamento
* Polimorfismo
* Interfaces
* Generics
* Sobrecarga de métodos
* Exceções customizadas
* Strategy Pattern
* Git
* GitHub

---

## 👥 Trabalho em equipe

O projeto foi desenvolvido colaborativamente, com divisão das responsabilidades por módulos.

Minha atuação esteve concentrada principalmente nos módulos de **Produtos, Clientes e Repositório Genérico**, posteriormente integrados às funcionalidades de vendas e demais componentes desenvolvidos pela equipe.

A colaboração e integração do código foram realizadas utilizando **Git e GitHub**.

---

## 🚀 Como executar

### 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

### 2. Abra o projeto em uma IDE

Recomenda-se utilizar uma IDE com suporte a Java, como:

* IntelliJ IDEA
* Eclipse
* VS Code

### 3. Execute a classe principal

```text
br.edu.cafeteria.app.Main
```

### 4. Utilize o menu

Após a execução, o sistema disponibilizará um menu interativo no terminal para gerenciamento da cafeteria.

---

## 📚 Objetivo acadêmico

O projeto teve como objetivo aplicar, em um sistema funcional, conceitos fundamentais de **Programação Orientada a Objetos e desenvolvimento de software**, especialmente a utilização de abstração, herança, polimorfismo, interfaces, Generics, tratamento de exceções e padrões de projeto.

---
