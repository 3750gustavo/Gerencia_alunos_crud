// app\src\main\java\com\example\myapplication\Conexao.java
package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco_alunos.db";
    private static final int VERSAO_BANCO = 1;

    public Conexao(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cpf TEXT NOT NULL," +
                "telefone TEXT NOT NULL," +
                "foto BLOB)"; // Add the foto column
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS alunos";
        db.execSQL(sql);
        onCreate(db);
    }
}