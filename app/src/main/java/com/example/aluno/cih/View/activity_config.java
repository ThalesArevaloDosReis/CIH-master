package com.example.aluno.cih.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.R;
import com.example.aluno.cih.ConexaoFirebase.FirebaseConexao;
import com.google.firebase.auth.FirebaseAuth;

public class activity_config extends AppCompatActivity {
    TextView txtEditarPerfil, txtAlterarSenha, txtExcluirConta, txtSair;
    String email;
    ListaUsuarios crud;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        email = getIntent().getStringExtra("userEmail");

        txtEditarPerfil = (TextView) findViewById(R.id.txtEditarPerfil);
        txtAlterarSenha = (TextView) findViewById(R.id.txtAlterarSenha);
        //txtExcluirConta = (TextView) findViewById(R.id.txtExcluirConta);
        txtSair = (TextView) findViewById(R.id.txtSair);

        crud = new ListaUsuarios(getBaseContext());

        txtEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(activity_config.this, EditarPerfil.class);
                it.putExtra("userEmail", email);
                startActivity(it);
            }
        });

        txtAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_config.this);
                builder.setTitle("Enviar link de redefinição de senha para " + email + "?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.sendPasswordResetEmail(email);
                        Toast.makeText(activity_config.this, "Link de redefinição de senha enviado para " + email, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Não", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        /*txtExcluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_config.this);
                builder.setTitle("Tem certeza de que deseja excluir sua conta?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        crud.removeDoBD(email);
                        Toast.makeText(activity_config.this, "Conta excluída", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(activity_config.this, activity_login.class);
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("Não", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });*/

        txtSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_config.this);
                builder.setTitle("Tem certeza de que deseja encerrar sua sessão?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();

                        SharedPreferences sharedPreferences = getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor;
                        editor = sharedPreferences.edit();
                        editor.putBoolean("Logado", false);
                        editor.putString("Email", "");
                        editor.commit();

                        Intent it = new Intent(activity_config.this, activity_login.class);
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("Não", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseConexao.getFirebaseAuth();
    }
}