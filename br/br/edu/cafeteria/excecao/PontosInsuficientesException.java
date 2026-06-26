
package br.edu.cafeteria.excecao;


public class PontosInsuficientesException extends Exception {
	private static final long serialVersionUID = 1L;

    private final int pontosNecessarios;
    private final int pontosDisponiveis;


    public PontosInsuficientesException(int pontosNecessarios, int pontosDisponiveis) {
        super(String.format(
                "Saldo de XP insuficiente para resgatar o pedido. " +
                        "Necessário: %d XP | Disponível: %d XP | Faltam: %d XP.",
                pontosNecessarios,
                pontosDisponiveis,
                pontosNecessarios - pontosDisponiveis
        ));
        this.pontosNecessarios  = pontosNecessarios;
        this.pontosDisponiveis  = pontosDisponiveis;
    }


    public int getPontosNecessarios() {
        return pontosNecessarios;
    }


    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }


    public int getPontosFaltantes() {
        return pontosNecessarios - pontosDisponiveis;
    }
}
