package br.edu.cafeteria.app;
import java.util.ArrayList;

import br.edu.cafeteria.servico.*;
import br.edu.cafeteria.excecao.*;
import br.edu.cafeteria.modelo.*;


public class Main {

	public static void main(String[] args) {
		RepositorioGenerico<Product> repoProduto = new RepositorioGenerico<>(new ArrayList<>());
        RepositorioGenerico<Cliente> repoCliente = new RepositorioGenerico<>(new ArrayList<>());
        
        //Cadastro do Cardápio
        Product pocaoMana = new Bebida("Poção de Mana","B01", 12.00, 5,  0.0, Tamanho.M);
        Product cafePro = new Bebida("Café do Programador","B02", 9.50,  8,  200.0, Tamanho.P);
        Product lembas = new Comida("Lembas Bread","C01", 8.00,  10, 5,  true,  false);
        Product portalCake = new Comida("Portal Cake", "C02", 14.00, 3,  20, false, true);
        repoProduto.Cadastrar(pocaoMana);
        repoProduto.Cadastrar(cafePro);
        repoProduto.Cadastrar(lembas);
        repoProduto.Cadastrar(portalCake);
        
        
    	//Cadastro de Clientes
    	Cliente c1 = new ClienteVIP("João Souza", "111.111.111-11");
    	repoCliente.Cadastrar(c1);
    	Cliente c2 = new ClienteStandard("Sofia Lima", "222.222.222-22");
    	repoCliente.Cadastrar(c2);
    	repoCliente.Ler();
    	
    	//CRUD - Edição e Remoção
    	 Product pocaoManaAtualizada = new Bebida("Poção de Mana (Refill)", "B01", 13.00, 5, 0.0, Tamanho.G);
         repoProduto.Editar(pocaoManaAtualizada);
         System.out.println("Produto B01 atualizado para: " + pocaoManaAtualizada.getNome());
         repoProduto.Editar(pocaoMana);
 
        
        //Polimorfismo por Sobrescrita
        try {
        	Venda v1 = new Venda("Atendente 1", c1);
        	v1.adicionarItem(portalCake);
        	v1.adicionarItem(cafePro, 2);
        	System.out.println(v1);
        	v1.finalizar();
        	System.out.printf("XP acumulado por %s (taxa x1): %d XP%n", c1.getNome(), c1.getSaldoXP());
        	
        } catch (EstoqueInsuficienteException e) {
            System.err.println("Erro na venda: " + e.getMessage());
        } catch (DadoVazioException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        }

        //Dia Promocional 
        try {
        	 Promocional diaGeek = new DescontoDiaGeek();
        	 Venda v2 = new Venda ("Atendente 1", c2);
        	 v2.adicionarItem(lembas);
        	 v2.adicionarItem(pocaoManaAtualizada, 2);
        	 v2.aplicarPromocao(diaGeek);
        	 System.out.println (v2);     
        	 v2.finalizar();
        } catch (EstoqueInsuficienteException e) {
        	System.err.println("Erro na venda: " + e.getMessage());
        } catch (DadoVazioException e) {
        	System.err.println("Erro de validação: " + e.getMessage());
        }
        
        //Tentativa de Resgate com XP Insuficiente
        try {
        	Venda v3 = new Venda("Atendente 2", c1);
        	v3.adicionarItem(portalCake, 2);
            System.out.printf("Saldo atual de %s: %d XP%n", c1.getNome(), c1.getSaldoXP());
 
            v3.finalizarComResgate();  //Lança a excessão
            System.out.println(v3);
        } catch (EstoqueInsuficienteException e) {
            System.err.println("Erro na venda: " + e.getMessage());
        } catch (PontosInsuficientesException e) {
        	System.out.println("PontosInsuficientesException capturada: " + e.getMessage());
        	System.out.printf("  Necessário: %d XP | Disponível: %d XP | Faltam: %d XP%n",
                e.getPontosNecessarios(), e.getPontosDisponiveis(), e.getPontosFaltantes());
        }
        
        //Resgate com XP
        c1.acumularXP(800.00);
        try {
        	Venda v4 = new Venda("Atendente 2", c1);
        	v4.adicionarItem(portalCake);
        	v4.finalizarComResgate();
        	System.out.println(v4);
        } catch (EstoqueInsuficienteException e) {
        	System.err.println("Erro na venda: " + e.getMessage());
        } catch (DadoVazioException e) {
        	System.err.println("Erro de validação: " + e.getMessage());
        } catch (PontosInsuficientesException e) {
        	System.out.println("[PontosInsuficientesException capturada]: " + e.getMessage());
        	System.out.printf("  Necessário: %d XP | Disponível: %d XP | Faltam: %d XP%n",
                e.getPontosNecessarios(), e.getPontosDisponiveis(), e.getPontosFaltantes());
        }
        
        
        //Modificadores Estáticos
        try {
            Venda extra = new Venda("Atendente Extra");
            extra.adicionarItem(cafePro);
            extra.finalizar();
            System.out.println("ID desta venda (gerado estaticamente): #" + extra.getId());
        } catch (EstoqueInsuficienteException e) {
            System.err.println(e.getMessage());
        }
        
        try {
            Venda vendaErrada = new Venda("Atendente");
            vendaErrada.adicionarItem(new Bebida("Teste", "T99", 1.0, 0, 0, Tamanho.P), 10);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }
        	
        
}
 