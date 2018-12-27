package com.example.aluno.cih.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.aluno.cih.Model.UserCurteCom;
import java.util.ArrayList;
import java.util.List;

public class ListaUserCurteCom {
    private SQLiteDatabase bd;
    private CriarBanco openHelper;

    public ListaUserCurteCom(Context contexto ){
        openHelper = new CriarBanco(contexto);
    }

    public void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    public void fechar(){
        bd.close();
    }

    public String addAoBD(String emailUser, int numCom){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.EMAIL_USER, emailUser);
        valores.put(openHelper.NUM_COM, numCom);

        resultado = bd.insert(openHelper.TABELA_USER_CURTE_COM,null, valores);
        fechar();

        if (resultado == -1)
            return "Erro ao curtir";
        else
            return "Publicação curtida";
    }

    public void removeDoBD(UserCurteCom ucc){
        String emailUser = ucc.getEmailUsuario();
        int numCom = ucc.getNumeroComentario();
        bd.delete(openHelper.TABELA_USER_CURTE_COM,"" + openHelper.EMAIL_USER + " = " + emailUser + " AND " + openHelper.NUM_COM + " = " + numCom,null);
    }

    public List<UserCurteCom> lerUsersCurtemComs(){
        abrir();
        List<UserCurteCom> ucc = new ArrayList<UserCurteCom>();
        Cursor cursor = bd.query(openHelper.TABELA_USER_CURTE_COM, null, null,null,null,null,null);
        while(cursor.moveToNext()){
            UserCurteCom uc = new UserCurteCom();
            uc.setEmailUsuario(cursor.getString(0));
            uc.setNumeroComentario(cursor.getInt(1));

            ucc.add(uc);
        }

        cursor.close();
        fechar();
        return ucc;
    }
}