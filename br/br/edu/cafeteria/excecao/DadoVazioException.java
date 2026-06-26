package br.edu.cafeteria.excecao;

public class DadoVazioException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public DadoVazioException(String message) {
        super(message);
    }
}
