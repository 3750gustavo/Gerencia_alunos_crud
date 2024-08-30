// app\src\main\java\com\example\myapplication\Pagamento.java
package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;

public class Pagamento implements Serializable {
    private int id;
    private int alunoId;
    private double valor;
    private Date data;

    public Pagamento(int alunoId, double valor, Date data) {
        this.alunoId = alunoId;
        this.valor = valor;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}