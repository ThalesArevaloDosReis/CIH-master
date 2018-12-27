package com.example.aluno.cih.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.aluno.cih.Model.Comentario;
import java.util.ArrayList;
import java.util.List;

public class ListaComentarios {
    private SQLiteDatabase bd;
    private CriarBanco openHelper;

    public ListaComentarios(Context contexto ){
        openHelper = new CriarBanco(contexto);
    }

    public void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    public void fechar(){
        bd.close();
    }

    public boolean addAoBD(int numero, String horaCom, String dataCom, String conteudo, String emailUser, int numPubl/*, int valor*/){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.NUMERO, numero);
        valores.put(openHelper.HORA_COM, horaCom);
        valores.put(openHelper.DATA_COM, dataCom);
        valores.put(openHelper.CONTEUDO, conteudo);
        valores.put(openHelper.EMAIL_USER, emailUser);
        valores.put(openHelper.NUM_PUB, numPubl);
        //valores.put(openHelper.VALOR, valor);

        resultado = bd.insert(openHelper.TABELA_COM,null, valores);
        fechar();

        if (resultado == -1)
            return false;
        else
            return true;
    }

    /*public boolean alterarValor(int numero, int valor){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.VALOR, valor);

        resultado = bd.update(openHelper.TABELA_COM, valores, "" + openHelper.NUMERO + " = '" + numero + "'",null);
        fechar();

        if (resultado == -1)
            return false;
        else
            return true;
    }*/

    public void removeDoBD(Comentario com){
        int num = com.getNumero();
        String email = com.getEmailUsuario();
        int numPubl = com.getNumeroPublicacao();
        bd.delete(openHelper.TABELA_COM,"" + openHelper.NUMERO + " = " + num + " AND " + openHelper.EMAIL_USER + " = " + email + " AND " + openHelper.NUM_PUB + " = " + numPubl,null);
    }

    public List<Comentario> lerComentarios(){
        abrir();
        List<Comentario> com = new ArrayList<Comentario>();
        Cursor cursor = bd.query(openHelper.TABELA_COM, null, null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Comentario c = new Comentario();
            c.setNumero(cursor.getInt(0));
            c.setHoraCom(cursor.getString(1));
            c.setDataCom(cursor.getString(2));
            c.setConteudo(cursor.getString(3));
            c.setEmailUsuario(cursor.getString(4));
            c.setNumeroPublicacao(cursor.getInt(5));
            //c.setValor(cursor.getInt(6));

            com.add(c);
        }

        cursor.close();
        fechar();
        return com;
    }
}