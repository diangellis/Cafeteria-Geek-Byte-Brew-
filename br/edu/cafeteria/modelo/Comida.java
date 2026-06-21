package edu.cafeteria.modelo;

public class Comida extends Product{
    private int tempo_Preparo;
    private boolean isVegano;
    private boolean contem_glutem;

    public Comida() {
    }

    public Comida(int tempo_Preparo, boolean contem_glutem, boolean isVegano) {
        this.tempo_Preparo = tempo_Preparo;
        this.contem_glutem = contem_glutem;
        this.isVegano = isVegano;
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
}
