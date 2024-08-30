// app\src\main\java\com\example\myapplication\PagamentoDao.java
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagamentoDao {
    private final SQLiteDatabase db;
    private final Conexao conexao;

    public PagamentoDao(Context context) {
        conexao = new Conexao(context);
        db = conexao.getWritableDatabase();
    }

    public Pagamento getPagamento(int id) {
        String[] colunas = {"id", "alunoId", "valor", "data"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("pagamentos", colunas, selection, selectionArgs, null, null, null);
        Pagamento pagamento = null;
        if (cursor.moveToFirst()) {
            pagamento = new Pagamento(cursor.getInt(1), cursor.getDouble(2), new Date(cursor.getLong(3)));
            pagamento.setId(cursor.getInt(0));
        }
        cursor.close();
        return pagamento;
    }

    public long inserir(Pagamento pagamento) {
        ContentValues values = new ContentValues();
        values.put("alunoId", pagamento.getAlunoId());
        values.put("valor", pagamento.getValor());
        values.put("data", pagamento.getData().getTime());

        return db.insert("pagamentos", null, values);
    }

    public int atualizar(Pagamento pagamento) {
        ContentValues values = new ContentValues();
        values.put("alunoId", pagamento.getAlunoId());
        values.put("valor", pagamento.getValor());
        values.put("data", pagamento.getData().getTime());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(pagamento.getId())};

        return db.update("pagamentos", values, selection, selectionArgs);
    }

    public int excluir(int id) {
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.delete("pagamentos", selection, selectionArgs);
    }

    public List<Pagamento> listar() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String[] colunas = {"id", "alunoId", "valor", "data"};
        Cursor cursor = db.query("pagamentos", colunas, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Pagamento pagamento = new Pagamento(cursor.getInt(1), cursor.getDouble(2), new Date(cursor.getLong(3)));
                pagamento.setId(cursor.getInt(0));
                pagamentos.add(pagamento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pagamentos;
    }

    public List<Pagamento> listarPorAluno(int alunoId) {
        List<Pagamento> pagamentos = new ArrayList<>();
        String[] colunas = {"id", "alunoId", "valor", "data"};
        String selection = "alunoId = ?";
        String[] selectionArgs = {String.valueOf(alunoId)};
        Cursor cursor = db.query("pagamentos", colunas, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Pagamento pagamento = new Pagamento(cursor.getInt(1), cursor.getDouble(2), new Date(cursor.getLong(3)));
                pagamento.setId(cursor.getInt(0));
                pagamentos.add(pagamento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pagamentos;
    }
}