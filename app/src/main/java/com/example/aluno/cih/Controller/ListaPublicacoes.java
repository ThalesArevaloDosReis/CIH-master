package com.example.aluno.cih.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.aluno.cih.Model.Publicacao;
import java.util.ArrayList;
import java.util.List;

public class ListaPublicacoes {
    private SQLiteDatabase bd;
    private CriarBanco openHelper;

    public ListaPublicacoes(Context contexto ){
        openHelper = new CriarBanco(contexto);
    }

    public void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    public void fechar(){
        bd.close();
    }

    public boolean addAoBD(int numero, String horaPub, String dataPub, String conteudo, String emailUser){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.NUMERO, numero);
        valores.put(openHelper.HORA_PUB, horaPub);
        valores.put(openHelper.DATA_PUB, dataPub);
        valores.put(openHelper.CONTEUDO, conteudo);
        valores.put(openHelper.EMAIL_USER, emailUser);

        resultado = bd.insert(openHelper.TABELA_PUBLIC,null, valores);
        fechar();

        if (resultado == -1)
            return false;
        else
            return true;
    }

    public void removeDoBD(Publicacao publ){
        int num = publ.getNumero();
        bd.delete(openHelper.TABELA_PUBLIC,"" + openHelper.NUMERO + " = " + num + "",null);
    }

    public List<Publicacao> lerPublicacoes(){
        abrir();
        List<Publicacao> publ = new ArrayList<Publicacao>();
        Cursor cursor = bd.query(openHelper.TABELA_PUBLIC, null, null,null,null,null,null);
        while(cursor.moveToNext()){
            Publicacao p = new Publicacao();
            p.setNumero(cursor.getInt(0));
            p.setHoraPub(cursor.getString(1));
            p.setDataPub(cursor.getString(2));
            p.setConteudo(cursor.getString(3));
            p.setEmailUsuario(cursor.getString(4));

            publ.add(p);
        }

        cursor.close();
        fechar();
        return publ;
    }
}