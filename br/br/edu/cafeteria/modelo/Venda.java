package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.EstoqueInsuficienteException;
import br.edu.cafeteria.excecao.PontosInsuficientesException;
import br.edu.cafeteria.servico.Promocional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venda {

    private static int contadorId = 0;

    private final int id;
    private final String nomeAtendente;
    private Cliente clienteCadastrado;
    private final List<ItemPedido> itens;
    private boolean finalizada;

    public Venda(String nomeAtendente) {
        this.id = ++contadorId;
        this.nomeAtendente = nomeAtendente;
        this.itens = new ArrayList<>();
        this.finalizada = false;
    }

    public Venda(String nomeAtendente, Cliente clienteCadastrado) {
        this(nomeAtendente);
        this.clienteCadastrado = clienteCadastrado;
    }

    // Sobrecarga 
    public void adicionarItem(Product produto) throws EstoqueInsuficienteException {
        adicionarItem(produto, 1);
    }

    // Sobrecarga 
    public void adicionarItem(Product produto, int quantidade) throws EstoqueInsuficienteException {
        if (produto.getQntd_estocada() < quantidade) {
            throw new EstoqueInsuficienteException(quantidade, produto.getQntd_estocada());
        }

        ItemPedido itemExistente = null;
        for (ItemPedido item : itens) {
            if (item.getProduto().getId().equals(produto.getId())) {
                itemExistente = item;
                break;
            }
        }
        itens.add(new ItemPedido(produto, quantidade));
    }

    public void atualizarPrecoDoItem(Product produto, double novoPreco) {
        for (ItemPedido item : itens) { // Aqui mexemos na lista real 'itens', que é mutável por dentro
            if (item.getProduto().getId().equals(produto.getId())) {
                item.setPrecoUnitarioAplicado(novoPreco);
                return;
            }
        }
    }

    public void aplicarPromocao(Promocional promocao) {
        promocao.aplicarDesconto(this);
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void finalizar() {
        if (finalizada) throw new IllegalStateException("Venda já finalizada.");

        double total = calcularTotal();

        for (ItemPedido item : itens) {
            item.getProduto().reduzirEstoque(item.getQuantidade());
        }

        if (clienteCadastrado != null) {
            clienteCadastrado.acumularXP(total);
        }

        finalizada = true;
        System.out.printf("Venda #%d finalizada. Total: R$%.2f%n", id, total);
    }

    public void finalizarComResgate() throws PontosInsuficientesException {
        if (finalizada) throw new IllegalStateException("Venda já finalizada.");

        if (!(clienteCadastrado instanceof ClienteVIP)) {
            throw new UnsupportedOperationException("Resgate de XP disponível apenas para ClienteVIP.");
        }

        double total = calcularTotal();
        ClienteVIP vip = (ClienteVIP) clienteCadastrado;
        vip.pagarComXP(total);

        for (ItemPedido item : itens) {
            item.getProduto().reduzirEstoque(item.getQuantidade());
        }

        finalizada = true;
        System.out.printf("Venda #%d paga com XP. Total: R$%.2f%n", id, total);
    }

    public int getId() { return id; }
    public String getNomeAtendente() { return nomeAtendente; }
    public Cliente getClienteCadastrado() { return clienteCadastrado; }
    public boolean isFinalizada() { return finalizada; }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public String toString() {
        String texto = "=== Venda #" + id + " | Atendente: " + nomeAtendente + " ===\n";

        for (ItemPedido item : itens) {
            texto += "  " + item + "\n";
        }


        texto += "TOTAL: R$" + String.format("%.2f", calcularTotal());

        return texto;
    }
}