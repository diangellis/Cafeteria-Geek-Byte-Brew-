package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.PontosInsuficientesException;


public class ClienteVIP extends Cliente {


    private static final int TAXA_XP = 2;


    public ClienteVIP(String nome, String cpf) {
        super(nome, cpf);
    }


    @Override
    public void acumularXP(double valorGasto) {
        if (valorGasto <= 0) return;
        int pontosGanhos = (int) (valorGasto * TAXA_XP);
        adicionarXP(pontosGanhos);
    }


    public void pagarComXP(double valorTotal) throws PontosInsuficientesException {

        int pontosNecessarios = (int) Math.ceil(valorTotal * PONTOS_POR_REAL);
        int pontosDisponiveis = getSaldoXP();

        if (pontosDisponiveis < pontosNecessarios) {
            throw new PontosInsuficientesException(pontosNecessarios, pontosDisponiveis);
        }

        debitarXP(pontosNecessarios);

        System.out.printf(
                "[VIP] Pagamento realizado com %d XP (equivalente a R$ %.2f). " +
                        "Saldo restante: %d XP.%n",
                pontosNecessarios, valorTotal, getSaldoXP()
        );
    }


    public int calcularCustoEmXP(double valorTotal) {
        return (int) Math.ceil(valorTotal * PONTOS_POR_REAL);
    }


    public boolean temXPSuficiente(double valorTotal) {
        return getSaldoXP() >= calcularCustoEmXP(valorTotal);
    }


    @Override
    public String toString() {
        return "VIP ★ " + super.toString();
    }
}
