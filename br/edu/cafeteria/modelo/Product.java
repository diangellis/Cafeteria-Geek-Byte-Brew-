package edu.cafeteria.modelo;

abstract class Product implements Entidade{
    private String nome;
    private String codigo;
    private double preco_Base;
    private int qntd_estocada;

    @Override
    public String getId(){
        return this.codigo;
    }

}
