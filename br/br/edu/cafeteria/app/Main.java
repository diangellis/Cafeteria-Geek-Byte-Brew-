package br.edu.cafeteria.app;
import java.util.ArrayList;

import br.edu.cafeteria.excecao.*;
import br.edu.cafeteria.modelo.*;


public class Main {

	public static void main(String[] args) {
		RepositorioGenerico<Product> repoProduto = new RepositorioGenerico<>(new ArrayList<>());
        RepositorioGenerico<Cliente> repoCliente = new RepositorioGenerico<>(new ArrayList<>());
        
        try {
        	Product p1 = new Bebida("Poção de Mana", "B01", 10.0, 5, 50.0, Tamanho.M);
        	Product p2 = new Comida("Lembas Bread", "C01", 5.0, 10, 5, true, false);
        	
        	repoProduto.Cadastrar(p1);
        	repoProduto.Cadastrar(p2);
        	
        	Cliente c1 = new ClienteVIP("João Souza", "111.111.111-11");
        	repoCliente.Cadastrar(c1);
        	
        	//Polimorfismo por Coerção e CRUD
        	System.out.println("\nLista de Produtos Cadastrados");
            repoProduto.Ler();
            
            Venda venda = new Venda("Atendente Geek", c1);
            venda.adicionarItem(p1, 2); // Polimorfismo por Sobrecarga
            venda.finalizar();
            
        } catch (EstoqueInsuficienteException e) {
            System.err.println("Erro na venda: " + e.getMessage());
        } catch (DadoVazioException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        }

        //Exceção
        try {
            Venda vendaErrada = new Venda("Atendente");
            vendaErrada.adicionarItem(new Bebida("Teste", "T99", 1.0, 0, 0, Tamanho.P), 10);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }
        	
        
}
 