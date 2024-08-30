// app\src\main\java\com\example\myapplication\AlunoDao.java
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {
    private final SQLiteDatabase db;
    private final Conexao conexao;

    public AlunoDao(Context context) {
        conexao = new Conexao(context);
        db = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno) {
        // Check for duplicates
        if (existeAlunoComCPF(aluno.getCpf()) || existeAlunoComTelefone(aluno.getTelefone())) {
            return -1; // Return -1 to indicate duplicate entry
        }

        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto()); // Store the profile picture
        values.put("ativo", aluno.isAtivo());
        values.put("curso", aluno.getCurso());

        return db.insert("alunos", null, values);
    }

    public int atualizar(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto()); // Store the profile picture
        values.put("ativo", aluno.isAtivo());
        values.put("curso", aluno.getCurso());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(aluno.getId())};

        return db.update("alunos", values, selection, selectionArgs);
    }

    private boolean existeAlunoComCPF(String cpf) {
        String[] colunas = {"id"};
        String selection = "cpf = ?";
        String[] selectionArgs = {cpf};
        Cursor cursor = db.query("alunos", colunas, selection, selectionArgs, null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    private boolean existeAlunoComTelefone(String telefone) {
        String[] colunas = {"id"};
        String selection = "telefone = ?";
        String[] selectionArgs = {telefone};
        Cursor cursor = db.query("alunos", colunas, selection, selectionArgs, null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        String[] colunas = {"id", "nome", "cpf", "telefone", "foto", "ativo", "curso"};
        Cursor cursor = db.query("alunos", colunas, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Aluno aluno = new Aluno(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                aluno.setId(cursor.getInt(0));
                byte[] foto = cursor.getBlob(4);
                if (foto != null) {
                    aluno.setFoto(foto);
                }
                aluno.setAtivo(cursor.getInt(5) == 1);
                aluno.setCurso(cursor.getString(6));
                alunos.add(aluno);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alunos;
    }

    public int excluir(int id) {
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.delete("alunos", selection, selectionArgs);
    }

    public Aluno getAluno(int id) {
        String[] colunas = {"id", "nome", "cpf", "telefone", "foto", "ativo", "curso"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("alunos", colunas, selection, selectionArgs, null, null, null);
        Aluno aluno = null;
        if (cursor.moveToFirst()) {
            aluno = new Aluno(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            aluno.setId(cursor.getInt(0));
            aluno.setFoto(cursor.getBlob(4));
            aluno.setAtivo(cursor.getInt(5) == 1);
            aluno.setCurso(cursor.getString(6));
        }
        cursor.close();
        return aluno;
    }
}