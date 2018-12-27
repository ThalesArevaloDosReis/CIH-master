package com.example.aluno.cih.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.cih.Adaptador.AdaptadorListaComent;
import com.example.aluno.cih.Adaptador.AdaptadorListaPubli;
import com.example.aluno.cih.Controller.ListaComentarios;
import com.example.aluno.cih.Controller.ListaPublicacoes;
import com.example.aluno.cih.Controller.ListaUserCurteCom;
import com.example.aluno.cih.Controller.ListaUsuarios;
import com.example.aluno.cih.Model.Comentario;
import com.example.aluno.cih.Model.Publicacao;
import com.example.aluno.cih.Model.UserCurteCom;
import com.example.aluno.cih.Model.Usuario;
import com.example.aluno.cih.R;

import java.util.ArrayList;
import java.util.List;

public class activity_comentario extends AppCompatActivity {

    Context myContext;

    TextView txt_com_nome;
    TextView txt_com_data;
    TextView txt_com_hora;
    EditText edt_com_conteudo;
    ImageView img_com_usuario;
    FloatingActionButton floating_add_comentario;

    private List<String> nomesList, dataList, horaList, conteudoList;
    private List<Integer> /*contList,*/ numeroComent;
    private List<Bitmap> imagensList;

    RecyclerView recycle_comentarios;
    private RecyclerView.LayoutManager comentariosLayoutManager;
    AdaptadorListaComent comentAdapter;

    AdaptadorListaComent.OnItemClickListener listener;

    ListaComentarios listaComentarios;
    ListaPublicacoes listaPublicacoes;
    ListaUsuarios listaUsuarios;

    SharedPreferences sharedPreferences;
    int numero_publicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        myContext = getBaseContext();

        listaComentarios = new ListaComentarios(myContext);
        listaPublicacoes = new ListaPublicacoes(myContext);
        listaUsuarios = new ListaUsuarios(myContext);

        txt_com_nome = (TextView) findViewById(R.id.txt_com_nome);
        txt_com_data = (TextView) findViewById(R.id.txt_com_data);
        txt_com_hora = (TextView) findViewById(R.id.txt_com_hora);
        edt_com_conteudo = (EditText) findViewById(R.id.edt_com_conteudo);
        img_com_usuario = (ImageView) findViewById(R.id.img_com_usuario);
        floating_add_comentario = (FloatingActionButton) findViewById(R.id.floating_add_comentario);

        lerPublicacao();

        lerComentarios();

        comentariosLayoutManager = new LinearLayoutManager(myContext);
        recycle_comentarios = (RecyclerView) findViewById(R.id.recycle_comentarios);
        recycle_comentarios.setHasFixedSize(true);
        recycle_comentarios.setLayoutManager(comentariosLayoutManager);

        listener = new AdaptadorListaComent.OnItemClickListener() {
            @Override
            public int onClick(View v,int position) {

                return 0;
            }
        };
        comentAdapter = new AdaptadorListaComent(myContext,nomesList,dataList,horaList,conteudoList,/*contList,*/imagensList,numeroComent,listener);
        recycle_comentarios.setAdapter(comentAdapter);

        floating_add_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_novo_comentario dialog = new activity_novo_comentario();
                dialog.setStyle(20,0);
                dialog.show(getSupportFragmentManager(),"novo");
            }
        });

    }

    public void lerPublicacao(){

        sharedPreferences = getSharedPreferences("CouldIHelp", Context.MODE_PRIVATE);
        numero_publicacao = sharedPreferences.getInt("numero_publicacao", 1);

        List<Publicacao> publicacao = listaPublicacoes.lerPublicacoes();


        ListaUsuarios lista = new ListaUsuarios(myContext);
        for(int i = publicacao.size() - 1; i >= 0; i--){
                if(numero_publicacao == publicacao.get(i).getNumero()){
                    for(com.example.aluno.cih.Model.Usuario u : lista.lerUsuarios()){
                        if(u.getEmail().equals(publicacao.get(i).getEmailUsuario())){
                            txt_com_nome.setText(u.getNome());
                            img_com_usuario.setImageBitmap(u.getImage());
                        }
                    }
                    txt_com_data.setText(publicacao.get(i).getDataPub());
                    txt_com_hora.setText(publicacao.get(i).getHoraPub());
                    edt_com_conteudo.setText(publicacao.get(i).getConteudo());
                }
        }

    }

    public void lerComentarios(){

        List<Comentario> comentarios = listaComentarios.lerComentarios();
        List<Usuario> usuarios = listaUsuarios.lerUsuarios();

        nomesList = new ArrayList<String>();
        dataList = new ArrayList<String>();
        horaList = new ArrayList<String>();
        conteudoList = new ArrayList<String>();
        //contList = new ArrayList<Integer>();
        numeroComent = new ArrayList<Integer>();
        imagensList = new ArrayList<Bitmap>();

        for(int i = comentarios.size() - 1; i >= 0; i--){
            if(numero_publicacao == comentarios.get(i).getNumeroPublicacao()){
                for(com.example.aluno.cih.Model.Usuario u : usuarios){
                    if(u.getEmail().equals(comentarios.get(i).getEmailUsuario())){
                        imagensList.add(u.getImage());
                        nomesList.add(u.getNome());
                    }
                }
                numeroComent.add(comentarios.get(i).getNumero());
                dataList.add(comentarios.get(i).getDataCom());
                horaList.add(comentarios.get(i).getHoraCom());
                conteudoList.add(comentarios.get(i).getConteudo());
                //contList.add(comentarios.get(i).getValor());
            }
        }

    }

}
