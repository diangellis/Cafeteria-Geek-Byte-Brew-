package edu.cafeteria.modelo;

public class Comida extends Product{
    private int tempo_Preparo;
    private boolean isVegano;
    private boolean contem_glutem;

    public Comida(String nome, String codigo, double preco_Base,
                  int qntd_estocada, int tempo_Preparo, boolean isVegano,
                  boolean contem_glutem) {
        super(nome, codigo, preco_Base, qntd_estocada);
        this.tempo_Preparo = tempo_Preparo;
        this.isVegano = isVegano;
        this.contem_glutem = contem_glutem;
    }

    public int getTempo_Preparo() {
        return tempo_Preparo;
    }

    public void setTempo_Preparo(int tempo_Preparo) {
        this.tempo_Preparo = tempo_Preparo;
    }

    public boolean isVegano() {
        return isVegano;
    }

    public void setVegano(boolean vegano) {
        isVegano = vegano;
    }

    public boolean isContem_glutem() {
        return contem_glutem;
    }

    public void setContem_glutem(boolean contem_glutem) {
        this.contem_glutem = contem_glutem;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(super.toString());
        s.append("Tempo de Preparo (min): ").append(this.tempo_Preparo).append("\n");
        s.append("É vegano: ").append(this.isVegano).append("\n");
        s.append("Tem glútem: ").append(contem_glutem).append("\n");
        return s.toString();

    }
}
