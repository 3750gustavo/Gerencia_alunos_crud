// app\src\main\java\com\example\myapplication\Aluno.java
package com.example.myapplication;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private byte[] foto; // New field to store the profile picture
    private boolean ativo; // New field to store active status
    private String curso; // New field to store course type
    private double valorPagamento;

    public Aluno(String nome, String cpf, String telefone, double aDouble) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nome: " + (nome != null ? nome : "") +
                ", CPF: " + (cpf != null ? cpf : "") +
                ", Telefone: " + (telefone != null ? telefone : "") +
                ", Ativo: " + ativo +
                ", Curso: " + (curso != null ? curso : "") +
                ", Valor do Pagamento: " + valorPagamento;
    }
}