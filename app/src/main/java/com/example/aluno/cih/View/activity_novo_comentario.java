package com.example.aluno.cih.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.cih.Controller.ListaComentarios;
import com.example.aluno.cih.Model.Comentario;
import com.example.aluno.cih.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class activity_novo_comentario extends DialogFragment {

    Context myContext;

    EditText edt_novo_comentario;
    Button btn_nov_comentar;
    Button btn_nov_com_cancelar;

    ListaComentarios listaComentarios;

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_novo_comentario, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaComentarios = new ListaComentarios(myContext);

        edt_novo_comentario = (EditText) getView().findViewById(R.id.edt_novo_comentario);
        btn_nov_com_cancelar = (Button) getView().findViewById(R.id.btn_nov_com_cancelar);
        btn_nov_comentar = (Button) getView().findViewById(R.id.btn_nov_comentar);

        btn_nov_com_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                startActivity(new Intent(getActivity(),activity_comentario.class));
            }
        });

        btn_nov_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentar();
            }
        });

    }

    public void comentar(){

        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = data.format(new Date());
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        String currentTime = hora.format(new Date());

        List<Comentario> comentariosList = listaComentarios.lerComentarios();
        int numero = comentariosList.size() + 1;

        SharedPreferences sharedPreferences = myContext.getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
        int numero_publicacao = sharedPreferences.getInt("numero_publicacao", 1);
        String email = sharedPreferences.getString("Email", "w");

        String conteudo = edt_novo_comentario.getText().toString();

        Log.e("numero", String.valueOf(numero));
        Log.e("hora",currentTime);
        Log.e("data",currentDate);
        Log.e("conteudo",conteudo);
        Log.e("email",email);
        Log.e("numero_publicacao", String.valueOf(numero_publicacao));

        boolean t_f = listaComentarios.addAoBD(numero,currentTime,currentDate,conteudo,email,numero_publicacao);

        if(t_f){
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            startActivity(new Intent(getActivity(),activity_comentario.class));
        }else{
            Toast.makeText(myContext,"Erro ao comentar!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(),activity_comentario.class));
        }

    }
}
