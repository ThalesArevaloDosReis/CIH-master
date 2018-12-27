package com.example.aluno.cih.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aluno.cih.Adaptador.AdaptadorListaPubli;
import com.example.aluno.cih.Controller.ListaPublicacoes;
import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.Model.Publicacao;
import com.example.aluno.cih.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_publicacao extends Fragment {

    Context myContext;

    public List<String> nomesList, dataList, horaList, conteudoList;
    public List<Integer> numerosList;
    public List<Bitmap> imagensList;

    RecyclerView recycler_publicacoes;
    private RecyclerView.LayoutManager publicacoesLayoutManager;
    AdaptadorListaPubli pubAdapter;

    AdaptadorListaPubli.OnItemClickListener listener;

    ListaPublicacoes listaPublicacoes;

    public fragment_publicacao() {
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
        return inflater.inflate(R.layout.fragment_publicacao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaPublicacoes = new ListaPublicacoes(myContext);

        lerPublicacoes();

        publicacoesLayoutManager = new LinearLayoutManager(myContext);
        recycler_publicacoes = (RecyclerView) getView().findViewById(R.id.recycle_publicacoes);
        recycler_publicacoes.setHasFixedSize(true);
        recycler_publicacoes.setLayoutManager(publicacoesLayoutManager);

        listener = new AdaptadorListaPubli.OnItemClickListener() {
            @Override
            public int onClick(View v,int position) {
                int posicaoPublicacao = position;
                SharedPreferences sharedPreferences = myContext.getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit();
                editor.putInt("numero_publicacao",posicaoPublicacao);
                editor.commit();

                startActivity(new Intent(getActivity(),activity_comentario.class));

                return 0;
            }
        };
        pubAdapter = new AdaptadorListaPubli(myContext,nomesList,dataList,horaList,conteudoList,imagensList,numerosList,listener);
        recycler_publicacoes.setAdapter(pubAdapter);

    }

    public void lerPublicacoes(){

        nomesList = new ArrayList<String>();
        dataList = new ArrayList<String>();
        horaList = new ArrayList<String>();
        conteudoList = new ArrayList<String>();
        imagensList = new ArrayList<Bitmap>();
        numerosList = new ArrayList<Integer>();

        List<Publicacao> publicacao = listaPublicacoes.lerPublicacoes();


        ListaUsuarios lista = new ListaUsuarios(myContext);
        for(int i = publicacao.size() - 1; i >= 0; i--){
            for(com.example.aluno.cih.Model.Usuario u : lista.lerUsuarios()){
                if(u.getEmail().equals(publicacao.get(i).getEmailUsuario())){
                    imagensList.add(u.getImage());
                    nomesList.add(u.getNome());
                }
            }
            numerosList.add(publicacao.get(i).getNumero());
            dataList.add(publicacao.get(i).getDataPub());
            horaList.add(publicacao.get(i).getHoraPub());
            conteudoList.add(publicacao.get(i).getConteudo());
        }
    }
}