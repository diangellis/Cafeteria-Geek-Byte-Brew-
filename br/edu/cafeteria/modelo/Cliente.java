package edu.cafeteria.modelo;

import edu.cafeteria.servico.Entidade;


public abstract class Cliente implements Entidade {


    public static final int PONTOS_POR_REAL = 10;


    private String nome;
    private String cpf;
    private int saldoXP;


    public Cliente(String nome, String cpf) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF do cliente não pode ser vazio.");
        }
        this.nome     = nome;
        this.cpf      = cpf;
        this.saldoXP  = 0;
    }


    public abstract void acumularXP(double valorGasto);


    protected void debitarXP(int pontos) {
        if (pontos > saldoXP) {
            throw new IllegalStateException(
                    "Tentativa de debitar mais XP do que o disponível. Use resgatarComXP() para validar antes."
            );
        }
        this.saldoXP -= pontos;
    }


    protected void adicionarXP(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Não é possível adicionar XP negativo.");
        }
        this.saldoXP += pontos;
    }


    @Override
    public String getId() {
        return cpf;
    }


    public String toString() {
        return String.format("[%s] %s | CPF: %s | XP: %d pts",
                getClass().getSimpleName(), nome, cpf, saldoXP);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }

    public int getSaldoXP() {
        return saldoXP;
    }




    }
