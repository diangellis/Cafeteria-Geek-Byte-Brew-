package edu.cafeteria.modelo;

public class Bebida extends Product {
    private double qnt_cafeina;
    private Tamanho tamanho;

    public Bebida(String nome, String codigo, double preco_Base, int qntd_estocada,
                  double qnt_cafeina, Tamanho tamanho) {
        super(nome, codigo, preco_Base, qntd_estocada);
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(super.toString());
        s.append("Cafeina (mg) : ").append(this.qnt_cafeina).append("\n");
        s.append("Tamanho: ").append(this.tamanho).append("\n");
        return s.toString();
    }
}
