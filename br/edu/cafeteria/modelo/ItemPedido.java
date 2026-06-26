package br.edu.cafeteria.modelo;

public class ItemPedido {

    private Product produto;
    private int quantidade;
    private double precoUnitarioAplicado;

    public ItemPedido(Product produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitarioAplicado = produto.getPreco();
    }

    public double getSubtotal() {
        return precoUnitarioAplicado * quantidade;
    }

    public Product getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitarioAplicado() { return precoUnitarioAplicado; }

    public void setPrecoUnitarioAplicado(double preco) {
        this.precoUnitarioAplicado = preco;
    }

    @Override
    public String toString() {
        return produto.getNome() + " x" + quantidade
                + " = R$" + String.format("%.2f", getSubtotal());
    }
}