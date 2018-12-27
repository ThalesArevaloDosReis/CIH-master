package com.example.aluno.cih.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.R;

public class fragment_conta extends Fragment {

    public ImageView img_conta_usuario, img_configuracao;
    public TextView txt_conta_nome, txt_conta_perfil;
    public String email;

    Context myContext;

    public fragment_conta() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_conta_usuario = (ImageView) getView().findViewById(R.id.imageView3);
        img_configuracao = (ImageView) getView().findViewById(R.id.btnConfig);
        txt_conta_nome = (TextView) getView().findViewById(R.id.txtNome);
        txt_conta_perfil = (TextView) getView().findViewById(R.id.txtConteudo);

        SharedPreferences sharedPreferences = myContext.getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("Email", "w");

        ListaUsuarios lista = new ListaUsuarios(myContext);
        for(com.example.aluno.cih.Model.Usuario u : lista.lerUsuarios()){
            if(u.getEmail().equals(email)){
                img_conta_usuario.setImageBitmap(u.getImage());
                txt_conta_nome.setText(u.getNome());
                txt_conta_perfil.setText(u.getPerfil());
            }
        }

        img_configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), activity_config.class);
                it.putExtra("userEmail", email);
                startActivity(it);
            }
        });
    }
}
