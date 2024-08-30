// app\src\main\java\com\example\myapplication\ListarPagamentosActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListarPagamentosActivity extends AppCompatActivity {

    private ArrayAdapter<Pagamento> adapter;
    private List<Pagamento> pagamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pagamentos);

        ListView listViewPagamentos = findViewById(R.id.listViewPagamentos);

        int alunoId = getIntent().getIntExtra("alunoId", -1);
        if (alunoId != -1) {
            PagamentoDao pagamentoDao = new PagamentoDao(this);
            pagamentos = pagamentoDao.listarPorAluno(alunoId);

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pagamentos);
            listViewPagamentos.setAdapter(adapter);
        }
    }
}