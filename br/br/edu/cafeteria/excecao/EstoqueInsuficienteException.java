package br.edu.cafeteria.excecao;

public class EstoqueInsuficienteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private final int quantidadeSolicitada;
    private final int quantidadeDisponivel;


    public EstoqueInsuficienteException(int quantidadeSolicitada, int quantidadeDisponivel) {

        super(String.format(
                "Estoque insuficiente. Solicitado: %d | Disponível: %d | Faltam: %d unidades.",
                quantidadeSolicitada,
                quantidadeDisponivel,
                quantidadeSolicitada - quantidadeDisponivel
        ));

        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }


    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public int getQuantidadeFaltante() {
        return quantidadeSolicitada - quantidadeDisponivel;
    }
}