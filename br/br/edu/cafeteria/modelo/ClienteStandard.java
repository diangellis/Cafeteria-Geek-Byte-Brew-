
package br.edu.cafeteria.modelo;


public class ClienteStandard extends Cliente {


    private static final int TAXA_XP = 1;


    public ClienteStandard(String nome, String cpf) {
        super(nome, cpf);
    }


    @Override
    public void acumularXP(double valorGasto) {
        if (valorGasto <= 0) return;
        int pontosGanhos = (int) (valorGasto * TAXA_XP);
        adicionarXP(pontosGanhos);
    }


    @Override
    public String toString() {
        return "Standard " + super.toString();
    }
}
