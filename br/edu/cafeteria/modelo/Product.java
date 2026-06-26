package br.edu.cafeteria.modelo;

import br.edu.cafeteria.servico.Entidade;

public abstract class Product implements Entidade {
    private String nome;
    private String codigo;
    private double preco_Base;
    private int qntd_estocada;

    public Product(String nome, String codigo, double preco_Base, int qntd_estocada) {
        this.nome = nome;
        this.codigo = codigo;
        this.preco_Base = preco_Base;
        this.qntd_estocada = qntd_estocada;
    }

    @Override
    public String getId() {
        return this.codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPreco_Base() {
        return preco_Base;
    }

    public int getQntd_estocada() {
        return qntd_estocada;
    }

    public void reduzirEstoque(int quantidade) {
    if (quantidade <= 0) {
        throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
    }

    if (quantidade > qntd_estocada) {
        throw new IllegalArgumentException("Estoque insuficiente.");
    }

    qntd_estocada -= quantidade;
}


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("nome: ").append(this.nome).append("\n");
        s.append("codigo: ").append(this.codigo).append("\n");
        s.append("preço base: ").append(this.preco_Base).append("\n");
        s.append("Estoque: ").append(this.qntd_estocada).append("\n");
        return s.toString();
    }
}