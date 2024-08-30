// app\src\main\java\com\example\myapplication\Conexao.java
package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco_alunos.db";
    private static final int VERSAO_BANCO = 2;

    public Conexao(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlAlunos = "CREATE TABLE alunos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cpf TEXT NOT NULL," +
                "telefone TEXT NOT NULL," +
                "foto BLOB," +
                "ativo INTEGER," +
                "curso TEXT)";
        db.execSQL(sqlAlunos);

        String sqlPagamentos = "CREATE TABLE pagamentos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "alunoId INTEGER," +
                "valor REAL," +
                "data INTEGER," +
                "FOREIGN KEY(alunoId) REFERENCES alunos(id) ON DELETE CASCADE)";
        db.execSQL(sqlPagamentos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlDropAlunos = "DROP TABLE IF EXISTS alunos";
        db.execSQL(sqlDropAlunos);

        String sqlDropPagamentos = "DROP TABLE IF EXISTS pagamentos";
        db.execSQL(sqlDropPagamentos);

        onCreate(db);
    }
}