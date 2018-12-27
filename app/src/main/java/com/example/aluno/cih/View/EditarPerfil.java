package com.example.aluno.cih.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EditarPerfil extends AppCompatActivity {
    Button btnSalvar, btnCancelar;
    ImageView img;
    public String email;
    private Bitmap bitmap;
    int REQUEST_CODE_GALLERY = 999;
    ListaUsuarios crud;
    EditText edNomeUsuario, edBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        email = getIntent().getStringExtra("userEmail");

        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);

        img = (ImageView) findViewById(R.id.image);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ActivityCompat.requestPermissions(EditarPerfil.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        crud = new ListaUsuarios(getBaseContext());

        edNomeUsuario = (EditText)findViewById(R.id.txtNome);
        edBio = (EditText)findViewById(R.id.txtBio);

        for(com.example.aluno.cih.Model.Usuario u : crud.lerUsuarios()){
            if(u.getEmail().equals(email)){
                img.setImageBitmap(u.getImage());
                edNomeUsuario.setText(u.getNome());
                edBio.setText(u.getPerfil());
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edNomeUsuario.getText().toString();
                String perfil = edBio.getText().toString();

                crud.alterarUser(email, nome, perfil, imageViewToByte(img));

                Intent it = new Intent(EditarPerfil.this, activity_config.class);
                it.putExtra("userEmail", email);
                startActivity(it);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EditarPerfil.this, activity_config.class);
                it.putExtra("userEmail", email);
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
                    .start(EditarPerfil.this);
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
}
