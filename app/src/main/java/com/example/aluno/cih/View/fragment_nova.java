package com.example.aluno.cih.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aluno.cih.Controller.ListaPublicacoes;
import com.example.aluno.cih.Model.Publicacao;
import com.example.aluno.cih.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class fragment_nova extends Fragment {

    Context myContext;

    TextInputEditText edt_nova_conteudo;
    Button btn_nova_publicar;
    Button btn_nova_cancelar;

    ListaPublicacoes listaPublicacoes;

    public fragment_nova() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nova, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaPublicacoes = new ListaPublicacoes(myContext);

        edt_nova_conteudo = (TextInputEditText) getView().findViewById(R.id.edt_nova_conteudo);
        btn_nova_cancelar = (Button) getView().findViewById(R.id.btn_nova_cancelar);
        btn_nova_publicar = (Button) getView().findViewById(R.id.btn_nova_publicar);

        btn_nova_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });

        btn_nova_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_nova_conteudo.setText("");
                startActivity(new Intent(getActivity(),fragment_publicacao.class));
            }
        });

    }

    public void publicar(){

        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = data.format(new Date());
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        String currentTime = hora.format(new Date());

        String conteudo = edt_nova_conteudo.getText().toString();

        List<Publicacao> publicacaoList = listaPublicacoes.lerPublicacoes();
        int numero = publicacaoList.size() + 1;

        SharedPreferences sharedPreferences = myContext.getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", "w");

        boolean t_f = listaPublicacoes.addAoBD(numero,currentTime,currentDate,conteudo,email);

        if(t_f){
            edt_nova_conteudo.setText("");
            Toast.makeText(myContext,"Publicado com sucesso!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(myContext,"Erro ao publicar!",Toast.LENGTH_LONG).show();
        }
    }
}
