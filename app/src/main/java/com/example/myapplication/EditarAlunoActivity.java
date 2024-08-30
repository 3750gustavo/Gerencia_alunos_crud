// app\src\main\java\com\example\myapplication\EditarAlunoActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class EditarAlunoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextTelefone;
    private ImageView imageViewFoto;
    private Button buttonTirarFoto;
    private CheckBox checkBoxAtivo;
    private RadioGroup radioGroupCurso;

    private Aluno aluno;
    private AlunoDao alunoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aluno);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCPF = findViewById(R.id.editTextCPF);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        Button buttonExcluir = findViewById(R.id.buttonExcluir);
        buttonTirarFoto = findViewById(R.id.buttonTirarFoto);
        imageViewFoto = findViewById(R.id.imageViewFoto);
        checkBoxAtivo = findViewById(R.id.checkBoxAtivo);
        radioGroupCurso = findViewById(R.id.radioGroupCurso);

        alunoDao = new AlunoDao(this);

        int alunoId = getIntent().getIntExtra("alunoId", -1);
        if (alunoId != -1) {
            aluno = alunoDao.getAluno(alunoId);
            if (aluno != null) {
                editTextNome.setText(aluno.getNome());
                editTextCPF.setText(aluno.getCpf());
                editTextTelefone.setText(aluno.getTelefone());
                checkBoxAtivo.setChecked(aluno.isAtivo());
                if (aluno.getCurso().equals("Graduação")) {
                    radioGroupCurso.check(R.id.radioButtonGraduacao);
                } else {
                    radioGroupCurso.check(R.id.radioButtonPosGraduacao);
                }
            }
        }

        if (aluno != null && aluno.getFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(aluno.getFoto(), 0, aluno.getFoto().length);
            imageViewFoto.setImageBitmap(bitmap);
        }

        buttonTirarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        });

        buttonSalvar.setOnClickListener(v -> {
            if (aluno != null) {
                aluno.setNome(editTextNome.getText().toString());
                aluno.setCpf(editTextCPF.getText().toString());
                aluno.setTelefone(editTextTelefone.getText().toString());
                aluno.setAtivo(checkBoxAtivo.isChecked());
                int selectedCursoId = radioGroupCurso.getCheckedRadioButtonId();
                String curso = selectedCursoId == R.id.radioButtonGraduacao ? "Graduação" : "Pós-graduação";
                aluno.setCurso(curso);

                Bitmap bitmap = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();
                aluno.setFoto(getBytesFromBitmap(bitmap));

                int result = alunoDao.atualizar(aluno);
                if (result > 0) {
                    Toast.makeText(EditarAlunoActivity.this, "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarAlunoActivity.this, "Erro ao atualizar aluno.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonExcluir.setOnClickListener(v -> {
            if (aluno != null) {
                int result = alunoDao.excluir(aluno.getId());
                if (result > 0) {
                    Toast.makeText(EditarAlunoActivity.this, "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarAlunoActivity.this, "Erro ao excluir aluno.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageViewFoto.setImageBitmap(bitmap);
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}