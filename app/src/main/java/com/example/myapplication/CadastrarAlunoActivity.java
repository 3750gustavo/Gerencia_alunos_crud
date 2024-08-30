// app\src\main\java\com\example\myapplication\CadastrarAlunoActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CadastrarAlunoActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextTelefone;
    private EditText editTextValorPagamento;
    private EditText editTextDataPagamento;
    private ImageView imageViewFoto;
    private CheckBox checkBoxAtivo;
    private RadioGroup radioGroupCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCPF = findViewById(R.id.editTextCPF);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextValorPagamento = findViewById(R.id.editTextValorPagamento);
        editTextDataPagamento = findViewById(R.id.editTextDataPagamento);
        Button buttonCadastrar = findViewById(R.id.buttonCadastrar);
        Button buttonTirarFoto = findViewById(R.id.buttonTirarFoto);
        imageViewFoto = findViewById(R.id.imageViewFoto);
        checkBoxAtivo = findViewById(R.id.checkBoxAtivo);
        radioGroupCurso = findViewById(R.id.radioGroupCurso);

        // Request camera permission if not already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        buttonCadastrar.setOnClickListener(v -> {
            if (validarCampos()) {
                String nome = editTextNome.getText().toString();
                String cpf = editTextCPF.getText().toString();
                String telefone = editTextTelefone.getText().toString();
                boolean ativo = checkBoxAtivo.isChecked();
                int selectedCursoId = radioGroupCurso.getCheckedRadioButtonId();
                String curso = selectedCursoId == R.id.radioButtonGraduacao ? "Graduação" : "Pós-graduação";

                Aluno aluno = new Aluno(nome, cpf, telefone);
                aluno.setAtivo(ativo);
                aluno.setCurso(curso);

                // Get the profile picture from the ImageView
                Bitmap bitmap = ((BitmapDrawable) imageViewFoto.getDrawable()).getBitmap();
                aluno.setFoto(getBytesFromBitmap(bitmap));

                AlunoDao alunoDao = new AlunoDao(CadastrarAlunoActivity.this);
                long id = alunoDao.inserir(aluno);
                if (id == -1) {
                    Toast.makeText(CadastrarAlunoActivity.this, "Aluno com CPF ou telefone já cadastrado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastrarAlunoActivity.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Insert payment if provided
                    if (!editTextValorPagamento.getText().toString().isEmpty() && !editTextDataPagamento.getText().toString().isEmpty()) {
                        double valor = Double.parseDouble(editTextValorPagamento.getText().toString());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
                        try {
                            Date data = simpleDateFormat.parse(editTextDataPagamento.getText().toString());
                            Pagamento pagamento = new Pagamento((int) id, valor, data);
                            PagamentoDao pagamentoDao = new PagamentoDao(CadastrarAlunoActivity.this);
                            pagamentoDao.inserir(pagamento);
                        } catch (ParseException e) {
                            // Handle the exception, e.g., log the error or display a toast message
                            android.util.Log.e("DATE_PARSING_ERROR", "Error parsing date", e);
                        }
                    }
                }
            }
        });

        buttonTirarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });
    }

    // Method to convert Bitmap to byte array
    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // Method to handle the camera intent result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageViewFoto.setImageBitmap(bitmap);
        }
    }

    // Handle camera permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to validate input fields
    private boolean validarCampos() {
        if (editTextNome.getText().toString().isEmpty()) {
            editTextNome.setError("Nome é obrigatório");
            return false;
        }
        if (editTextCPF.getText().toString().isEmpty()) {
            editTextCPF.setError("CPF é obrigatório");
            return false;
        }
        if (editTextTelefone.getText().toString().isEmpty()) {
            editTextTelefone.setError("Telefone é obrigatório");
            return false;
        }
        if (!editTextValorPagamento.getText().toString().isEmpty() && !editTextDataPagamento.getText().toString().isEmpty()) {
            try {
                Double.parseDouble(editTextValorPagamento.getText().toString());
            } catch (NumberFormatException e) {
                editTextValorPagamento.setError("Valor do pagamento inválido");
                return false;
            }
        }
        return true;
    }
}