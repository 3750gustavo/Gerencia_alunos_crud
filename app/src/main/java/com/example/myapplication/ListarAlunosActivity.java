// app\src\main\java\com\example\myapplication\ListarAlunosActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

    private ArrayAdapter<Aluno> adapter;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        ListView listViewAlunos = findViewById(R.id.listViewAlunos);

        AlunoDao alunoDao = new AlunoDao(this);
        alunos = alunoDao.listar();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listViewAlunos.setAdapter(adapter);

        listViewAlunos.setOnItemClickListener((parent, view, position, id) -> {
            Aluno aluno = (Aluno) parent.getItemAtPosition(position);
            Intent intent = new Intent(ListarAlunosActivity.this, EditarAlunoActivity.class);
            intent.putExtra("alunoId", aluno.getId());
            startActivity(intent);
        });

        listViewAlunos.setOnItemLongClickListener((parent, view, position, id) -> {
            Aluno aluno = (Aluno) parent.getItemAtPosition(position);
            Intent intent = new Intent(ListarAlunosActivity.this, ListarPagamentosActivity.class);
            intent.putExtra("alunoId", aluno.getId());
            startActivity(intent);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        AlunoDao alunoDao = new AlunoDao(this);
        alunos.clear();
        alunos.addAll(alunoDao.listar());
        adapter.notifyDataSetChanged();
    }
}