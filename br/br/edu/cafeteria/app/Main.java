package br.edu.cafeteria.app;

import java.util.ArrayList;
import java.util.Scanner;

import br.edu.cafeteria.excecao.*;
import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.servico.DescontoDiaGeek;
import br.edu.cafeteria.servico.Promocional;

public class Main {

    static final Scanner sc = new Scanner(System.in);
    static final java.util.List<Product> produtos  = new ArrayList<>();
    static final java.util.List<Cliente> clientes  = new ArrayList<>();
    static final RepositorioGenerico<Product> repoProduto = new RepositorioGenerico<>(new ArrayList<>());
    static final RepositorioGenerico<Cliente> repoCliente = new RepositorioGenerico<>(new ArrayList<>());

    public static void main(String[] args) {
        popularDados();
        int op;
        do {
            cabecalho("BYTE & BREW ☕");
            System.out.println("  1. Ver cardápio e fazer pedido");
            System.out.println("  2. Clientes");
            System.out.println("  3. Produtos");
            System.out.println("  0. Sair");
            linha();
            op = lerInt("Opção: ");
            switch (op) {
                case 1 -> fluxoVenda();
                case 2 -> menuClientes();
                case 3 -> menuProdutos();
                case 0 -> System.out.println("\nAté logo! ☕");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
        sc.close();
    }

    static void fluxoVenda() {
        // Exibe cardápio
        cabecalho("CARDÁPIO");
        if (produtos.isEmpty()) { System.out.println("Nenhum produto cadastrado."); return; }
        for (Product p : produtos) {
            String tipo = (p instanceof Bebida) ? "🥤" : "🍽";
            System.out.printf("  %s [%s] %-25s R$ %.2f  (estoque: %d)%n",
                    tipo, p.getCodigo(), p.getNome(), p.getPreco_Base(), p.getQntd_estocada());
        }
        linha();

        // Identifica atendente
        System.out.print("Nome do atendente: ");
        String atendente = sc.nextLine().trim();

        // Identifica cliente (opcional)
        System.out.print("CPF do cliente (Enter para pular): ");
        String cpf = sc.nextLine().trim();
        Cliente cliente = cpf.isBlank() ? null : buscarCliente(cpf);
        if (!cpf.isBlank() && cliente == null)
            System.out.println("CPF não encontrado — prosseguindo como cliente casual.");
        else if (cliente != null)
            System.out.println("Olá, " + cliente.getNome() + "! Saldo: " + cliente.getSaldoXP() + " XP");

        Venda venda = (cliente != null) ? new Venda(atendente, cliente) : new Venda(atendente);

        // Loop de itens
        boolean continuar = true;
        while (continuar) {
            System.out.print("\nCódigo do produto (Enter para fechar pedido): ");
            String cod = sc.nextLine().trim();
            if (cod.isBlank()) { continuar = false; continue; }

            Product p = buscarProduto(cod);
            if (p == null) { System.out.println("Produto não encontrado."); continue; }

            int qtd = lerInt("Quantidade: ");
            try {
                venda.adicionarItem(p, qtd);
                System.out.printf("  ✔ %dx %s — R$ %.2f%n", qtd, p.getNome(), p.getPreco_Base() * qtd);
            } catch (EstoqueInsuficienteException e) {
                System.out.println("  ✘ " + e.getMessage());
            }
        }

        if (venda.getItens().isEmpty()) { System.out.println("Pedido vazio. Venda cancelada."); return; }

        // Promoção
        System.out.print("\nAplicar Promoção Dia Geek? 10% em bebidas (s/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            Promocional promo = new DescontoDiaGeek();
            venda.aplicarPromocao(promo);
            System.out.println("Desconto aplicado!");
        }

        // Resumo
        System.out.println("\n" + venda);

        // Forma de pagamento
        boolean vip = cliente instanceof ClienteVIP;
        System.out.println("\nForma de pagamento:");
        System.out.println("  1. Dinheiro / Cartão");
        if (vip) System.out.printf("  2. Resgatar XP (saldo: %d XP | necessário: %d XP)%n",
                cliente.getSaldoXP(), ((ClienteVIP) cliente).calcularCustoEmXP(venda.calcularTotal()));
        int pag = lerInt("Opção: ");

        if (pag == 2 && vip) {
            try {
                venda.finalizarComResgate();
                System.out.printf("Saldo restante: %d XP%n", cliente.getSaldoXP());
            } catch (PontosInsuficientesException e) {
                System.out.println("XP insuficiente: " + e.getMessage());
                System.out.printf("Faltam %d XP. Pagando em dinheiro...%n", e.getPontosFaltantes());
                venda.finalizar();
            }
        } else {
            venda.finalizar();
            if (cliente != null)
                System.out.printf("+%d XP para %s! Saldo: %d XP%n",
                        (int)(venda.calcularTotal() * (vip ? 2 : 1)),
                        cliente.getNome(), cliente.getSaldoXP());
        }
    }

    static void menuClientes() {
        int op;
        do {
            cabecalho("CLIENTES");
            System.out.println("  1. Listar clientes");
            System.out.println("  2. Cadastrar Standard");
            System.out.println("  3. Cadastrar VIP");
            System.out.println("  4. Remover cliente");
            System.out.println("  0. Voltar");
            linha();
            op = lerInt("Opção: ");
            switch (op) {
                case 1 -> listarClientes();
                case 2 -> cadastrarCliente(false);
                case 3 -> cadastrarCliente(true);
                case 4 -> removerCliente();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    static void listarClientes() {
        if (clientes.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); return; }
        cabecalho("LISTA DE CLIENTES");
        for (Cliente c : clientes)
            System.out.println("  " + c);
    }

    static void cadastrarCliente(boolean vip) {
        System.out.print("Nome: ");  String nome = sc.nextLine().trim();
        System.out.print("CPF: ");   String cpf  = sc.nextLine().trim();
        try {
            Cliente c = vip ? new ClienteVIP(nome, cpf) : new ClienteStandard(nome, cpf);
            repoCliente.Cadastrar(c);
            clientes.add(c);
            System.out.println("Cliente " + (vip ? "VIP ★" : "Standard") + " cadastrado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void removerCliente() {
        System.out.print("CPF do cliente: ");
        Cliente c = buscarCliente(sc.nextLine().trim());
        if (c == null) { System.out.println("Cliente não encontrado."); return; }
        repoCliente.Deletar(c);
        clientes.remove(c);
        System.out.println("Cliente removido: " + c.getNome());
    }

    static void menuProdutos() {
        int op;
        do {
            cabecalho("PRODUTOS");
            System.out.println("  1. Listar produtos");
            System.out.println("  2. Cadastrar Bebida");
            System.out.println("  3. Cadastrar Comida");
            System.out.println("  4. Editar produto");
            System.out.println("  5. Remover produto");
            System.out.println("  0. Voltar");
            linha();
            op = lerInt("Opção: ");
            switch (op) {
                case 1 -> repoProduto.Ler();
                case 2 -> cadastrarBebida();
                case 3 -> cadastrarComida();
                case 4 -> editarProduto();
                case 5 -> removerProduto();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    static void cadastrarBebida() {
        System.out.print("Nome: ");       String nome   = sc.nextLine().trim();
        System.out.print("Código: ");     String codigo = sc.nextLine().trim();
        double preco   = lerDouble("Preço (R$): ");
        int estoque    = lerInt("Estoque: ");
        double cafeina = lerDouble("Cafeína (mg): ");
        Tamanho tam    = lerTamanho();
        Bebida b = new Bebida(nome, codigo, preco, estoque, cafeina, tam);
        repoProduto.Cadastrar(b);
        produtos.add(b);
        System.out.println("Bebida cadastrada!");
    }

    static void cadastrarComida() {
        System.out.print("Nome: ");   String nome   = sc.nextLine().trim();
        System.out.print("Código: "); String codigo = sc.nextLine().trim();
        double preco = lerDouble("Preço (R$): ");
        int estoque  = lerInt("Estoque: ");
        int preparo  = lerInt("Tempo de preparo (min): ");
        System.out.print("É vegano? (s/n): ");
        boolean vegano = sc.nextLine().trim().equalsIgnoreCase("s");
        System.out.print("Contém glúten? (s/n): ");
        boolean gluten = sc.nextLine().trim().equalsIgnoreCase("s");
        Comida c = new Comida(nome, codigo, preco, estoque, preparo, vegano, gluten);
        repoProduto.Cadastrar(c);
        produtos.add(c);
        System.out.println("Comida cadastrada!");
    }

    static void editarProduto() {
        System.out.print("Código do produto: ");
        Product atual = buscarProduto(sc.nextLine().trim());
        if (atual == null) { System.out.println("Produto não encontrado."); return; }
        System.out.println("Produto atual:\n" + atual);

        if (atual instanceof Bebida b) {
            String nome    = lerCampo("Nome",           atual.getNome());
            double preco   = lerDoubleCampo("Preço",    atual.getPreco_Base());
            int estoque    = lerIntCampo("Estoque",      atual.getQntd_estocada());
            double cafeina = lerDoubleCampo("Cafeína",  b.getQnt_cafeina());
            Tamanho tam    = lerTamanho();
            Product novo = new Bebida(nome, atual.getCodigo(), preco, estoque, cafeina, tam);
            repoProduto.Editar(novo);
            produtos.set(produtos.indexOf(atual), novo);
        } else if (atual instanceof Comida c) {
            String nome  = lerCampo("Nome",            atual.getNome());
            double preco = lerDoubleCampo("Preço",     atual.getPreco_Base());
            int estoque  = lerIntCampo("Estoque",      atual.getQntd_estocada());
            int preparo  = lerIntCampo("Preparo (min)", c.getTempo_Preparo());
            System.out.print("Vegano? (s/n): ");
            boolean vegano = sc.nextLine().trim().equalsIgnoreCase("s");
            System.out.print("Glúten? (s/n): ");
            boolean gluten = sc.nextLine().trim().equalsIgnoreCase("s");
            Product novo = new Comida(nome, atual.getCodigo(), preco, estoque, preparo, vegano, gluten);
            repoProduto.Editar(novo);
            produtos.set(produtos.indexOf(atual), novo);
        }
        System.out.println("Produto atualizado!");
    }

    static void removerProduto() {
        System.out.print("Código do produto: ");
        Product p = buscarProduto(sc.nextLine().trim());
        if (p == null) { System.out.println("Produto não encontrado."); return; }
        repoProduto.Deletar(p);
        produtos.remove(p);
        System.out.println("Produto removido: " + p.getNome());
    }

    static Product buscarProduto(String codigo) {
        for (Product p : produtos)
            if (p.getId().equalsIgnoreCase(codigo)) return p;
        return null;
    }

    static Cliente buscarCliente(String cpf) {
        for (Cliente c : clientes)
            if (c.getId().equals(cpf)) return c;
        return null;
    }

    static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro."); }
        }
    }

    static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim().replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Digite um número válido."); }
        }
    }

    static String lerCampo(String campo, String atual) {
        System.out.printf("%s [%s]: ", campo, atual);
        String v = sc.nextLine().trim();
        return v.isBlank() ? atual : v;
    }

    static double lerDoubleCampo(String campo, double atual) {
        System.out.printf("%s [%.2f]: ", campo, atual);
        String v = sc.nextLine().trim();
        if (v.isBlank()) return atual;
        try { return Double.parseDouble(v.replace(',', '.')); }
        catch (NumberFormatException e) { return atual; }
    }

    static int lerIntCampo(String campo, int atual) {
        System.out.printf("%s [%d]: ", campo, atual);
        String v = sc.nextLine().trim();
        if (v.isBlank()) return atual;
        try { return Integer.parseInt(v); }
        catch (NumberFormatException e) { return atual; }
    }

    static Tamanho lerTamanho() {
        while (true) {
            System.out.print("Tamanho (P/M/G): ");
            try { return Tamanho.valueOf(sc.nextLine().trim().toUpperCase()); }
            catch (IllegalArgumentException e) { System.out.println("Use P, M ou G."); }
        }
    }

    static void cabecalho(String titulo) {
        System.out.println("\n══════════════════════════════════════");
        System.out.println("  " + titulo);
        System.out.println("══════════════════════════════════════");
    }

    static void linha() {
        System.out.println("──────────────────────────────────────");
    }


    static void popularDados() {
        adicionarProduto(new Bebida("Poção de Mana",       "B01", 12.00, 5,   0.0, Tamanho.M));
        adicionarProduto(new Bebida("Café do Programador", "B02",  9.50, 8, 200.0, Tamanho.P));
        adicionarProduto(new Comida("Lembas Bread",        "C01",  8.00, 10,  5, true,  false));
        adicionarProduto(new Comida("Portal Cake",         "C02", 14.00,  3, 20, false, true));
        adicionarCliente(new ClienteVIP("João Souza",      "111.111.111-11"));
        adicionarCliente(new ClienteStandard("Sofia Lima", "222.222.222-22"));
    }

    static void adicionarProduto(Product p) { repoProduto.Cadastrar(p); produtos.add(p); }
    static void adicionarCliente(Cliente c) { repoCliente.Cadastrar(c); clientes.add(c); }
}