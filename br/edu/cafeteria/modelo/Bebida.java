package edu.cafeteria.modelo;

import edu.cafeteria.modelo.Tamanho;

public class Bebida extends Product{
    private double qnt_cafeina;
    private Tamanho tamanho;

    public Bebida() {
    }

    public Bebida(double qnt_cafeina, Tamanho tamanho) {
        this.qnt_cafeina = qnt_cafeina;
        this.tamanho = tamanho;
    }

    public double getQnt_cafeina() {
        return qnt_cafeina;
    }

    public void setQnt_cafeina(double qnt_cafeina) {
        this.qnt_cafeina = qnt_cafeina;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }
}
