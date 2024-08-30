// app\src\main\java\com\example\myapplication\EditarPagamentoActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditarPagamentoActivity extends AppCompatActivity {

    private EditText editTextValorPagamento;
    private EditText editTextDataPagamento;
    private Pagamento pagamento;
    private PagamentoDao pagamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pagamento);

        editTextValorPagamento = findViewById(R.id.editTextValorPagamento);
        editTextDataPagamento = findViewById(R.id.editTextDataPagamento);
        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        Button buttonExcluir = findViewById(R.id.buttonExcluir);

        pagamentoDao = new PagamentoDao(this);

        int pagamentoId = getIntent().getIntExtra("pagamentoId", -1);
        if (pagamentoId != -1) {
            pagamento = pagamentoDao.getPagamento(pagamentoId);
            if (pagamento != null) {
                editTextValorPagamento.setText(String.valueOf(pagamento.getValor()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
                editTextDataPagamento.setText(simpleDateFormat.format(pagamento.getData()));
            }
        }

        buttonSalvar.setOnClickListener(v -> {
            if (pagamento != null) {
                pagamento.setValor(Double.parseDouble(editTextValorPagamento.getText().toString()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
                try {
                    Date data = simpleDateFormat.parse(editTextDataPagamento.getText().toString());
                    pagamento.setData(data);
                } catch (ParseException e) {
                    android.util.Log.e("DATE_PARSING_ERROR", "Error parsing date", e);
                }

                int result = pagamentoDao.atualizar(pagamento);
                if (result > 0) {
                    Toast.makeText(EditarPagamentoActivity.this, "Pagamento atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarPagamentoActivity.this, "Erro ao atualizar pagamento.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonExcluir.setOnClickListener(v -> {
            if (pagamento != null) {
                int result = pagamentoDao.excluir(pagamento.getId());
                if (result > 0) {
                    Toast.makeText(EditarPagamentoActivity.this, "Pagamento excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarPagamentoActivity.this, "Erro ao excluir pagamento.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}