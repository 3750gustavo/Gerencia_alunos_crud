// app\src\main\java\com\example\myapplication\ListarPagamentosActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

public class ListarPagamentosActivity extends AppCompatActivity {

    private ArrayAdapter<Pagamento> adapter;
    private List<Pagamento> pagamentos;
    private int alunoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pagamentos);

        ListView listViewPagamentos = findViewById(R.id.listViewPagamentos);
        Button buttonAdicionarPagamento = findViewById(R.id.buttonAdicionarPagamento);

        alunoId = getIntent().getIntExtra("alunoId", -1);
        if (alunoId != -1) {
            PagamentoDao pagamentoDao = new PagamentoDao(this);
            pagamentos = pagamentoDao.listarPorAluno(alunoId);

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pagamentos);
            listViewPagamentos.setAdapter(adapter);
        }

        listViewPagamentos.setOnItemClickListener((parent, view, position, id) -> {
            Pagamento pagamento = (Pagamento) parent.getItemAtPosition(position);
            Intent intent = new Intent(ListarPagamentosActivity.this, EditarPagamentoActivity.class);
            intent.putExtra("pagamentoId", pagamento.getId());
            startActivity(intent);
        });

        buttonAdicionarPagamento.setOnClickListener(v -> {
            if (alunoId != -1) {
                AlunoDao alunoDao = new AlunoDao(this);
                Aluno aluno = alunoDao.getAluno(alunoId);
                if (aluno != null) {
                    double valorPagamento = aluno.getValorPagamento(); // Use the payment value from the student
                    Pagamento pagamento = new Pagamento(alunoId, valorPagamento, new Date());
                    PagamentoDao pagamentoDao = new PagamentoDao(this);
                    long id = pagamentoDao.inserir(pagamento);
                    if (id != -1) {
                        Toast.makeText(ListarPagamentosActivity.this, "Pagamento adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                        refreshList();
                    } else {
                        Toast.makeText(ListarPagamentosActivity.this, "Erro ao adicionar pagamento.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void refreshList() {
        PagamentoDao pagamentoDao = new PagamentoDao(this);
        pagamentos.clear();
        pagamentos.addAll(pagamentoDao.listarPorAluno(alunoId));
        adapter.notifyDataSetChanged();
    }
}