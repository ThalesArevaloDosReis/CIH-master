package com.example.aluno.cih.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.cih.ConexaoFirebase.FirebaseConexao;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.R;
// classe do cadastro

public class activity_cadastro extends AppCompatActivity {
    Button btnCadastro, btnCancelar;
    TextView lblTermos;
    ImageView img;
    private Bitmap bitmap;
    int REQUEST_CODE_GALLERY = 999;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadastro = (Button)findViewById(R.id.btnCadastro);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);

        lblTermos = (TextView) findViewById(R.id.lblTermos);
        lblTermos.setText(Html.fromHtml("<u>Termos de Uso.</u>"));

        img = (ImageView) findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ActivityCompat.requestPermissions(activity_cadastro.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        lblTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_cadastro.this, activity_termos.class);
                startActivity(i);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaUsuarios crud = new ListaUsuarios(getBaseContext());

                EditText edEmail = (EditText)findViewById(R.id.txtEmail);
                EditText edNomeUsuario = (EditText)findViewById(R.id.txtNomeU);
                EditText edSenha = (EditText)findViewById(R.id.txtSenha1);
                EditText edConfirmarSenha = (EditText)findViewById(R.id.txtSenha2);

                String email = edEmail.getText().toString();
                String nome = edNomeUsuario.getText().toString();
                String senha = edSenha.getText().toString();
                String confirmarSenha = edConfirmarSenha.getText().toString();
                String perfil = "";

                if(senha.equals(confirmarSenha)){
                    if(senha.length() >= 6){
                        firebaseAuth.createUserWithEmailAndPassword(email,senha);
                        crud.addAoBD(email, nome, senha, perfil, imageViewToByte(img));

                        SharedPreferences sharedPreferences = getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor;
                        editor = sharedPreferences.edit();
                        editor.putBoolean("Logado", true);
                        editor.putString("Email", email);
                        editor.commit();

                        Intent it = new Intent(activity_cadastro.this, activity_principal.class);
                        it.putExtra("userEmail", email);
                        startActivity(it);
                    }else{
                        Toast.makeText(getApplicationContext(), "A senha deve conter ao menos seis caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Senha incompatível com a senha confirmada", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(activity_cadastro.this, activity_login.class);
                startActivity(it);
            }
        });
    }

    public static byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(this, "Sem permissão para acessar localização do arquivo", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            com.theartofdev.edmodo.cropper.CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(activity_cadastro.this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                img.setImageURI(resultUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseConexao.getFirebaseAuth();
    }
}