package edu.cafeteria.modelo;

import java.util.List;

public class RepositorioGenerico<T extends Entidade>{

private final List<T> banco_de_dados;

    public RepositorioGenerico(List<T> banco_de_dados) {
        this.banco_de_dados = banco_de_dados;
    }

    public void Cadastrar(T dado){
        if(dado == null){
            System.out.println("Dado vazio!");
            return;
        }

        for(T item : banco_de_dados){
            if(item.getId().equals(dado.getId())){
                System.out.println("Cadastro já realizado!");
                return;
            }
        }

        banco_de_dados.add(dado);
        System.out.println("Cadastrado com sucesso!");
    }

    public T Deletar(T dado){

        if(dado == null){
            System.out.println("Dado inválido!");
            return null;
        }

        for(T item : banco_de_dados){
            if(item.getId().equals(dado.getId())){
                banco_de_dados.remove(item);
                System.out.println("Removido com Sucesso!");
                return item;
            }
        }
        System.out.println("Dado não localizado!");
        return null;
    }
}
