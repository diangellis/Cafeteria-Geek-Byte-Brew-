package edu.cafeteria.modelo;

import edu.cafeteria.excecao.DadoVazioException;

import java.util.List;

public class RepositorioGenerico<T extends Entidade>{

private final List<T> banco_de_dados;

    public RepositorioGenerico(List<T> banco_de_dados) {
        this.banco_de_dados = banco_de_dados;
    }

    public void Cadastrar(T dado){
        if(dado == null){
            throw new DadoVazioException("Dado Vazio");
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
            throw new DadoVazioException("Dado Vazio");
        }

        for(T item : banco_de_dados){
            if(item.getId().equals(dado.getId())){
                banco_de_dados.remove(item);
                System.out.println("Removido com Sucesso!");
                return item;
            }
        }
        throw new DadoVazioException("Dado Não Econtrado!");

    }

    public void Editar(T dadoAtualizado){
        if(dadoAtualizado == null){
            throw new DadoVazioException("Dado Vazio");
        }

        for(int i = 0; i < banco_de_dados.size(); i++){
            if(banco_de_dados.get(i).getId().equals(dadoAtualizado.getId())){
                banco_de_dados.set(i, dadoAtualizado);
                System.out.println("Dado atualizado com sucesso!");
                return;
            }
        }
        throw new DadoVazioException("Dado Não Ecnontrado");
    }

    public void Ler(){
        for(int i = 0; i < banco_de_dados.size(); i++){
            System.out.println(banco_de_dados.get(i));
        }
        return;
    }

}
