package com.example.aluno.cih.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.aluno.cih.Model.UserDenunciaCom;
import java.util.ArrayList;
import java.util.List;

public class ListaUserDenunciaCom {
    private SQLiteDatabase bd;
    private CriarBanco openHelper;

    public ListaUserDenunciaCom(Context contexto ){
        openHelper = new CriarBanco(contexto);
    }

    public void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    public void fechar(){
        bd.close();
    }

    public String addAoBD(String emailUser, int numCom, int numPubl, String motivo){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.EMAIL_USER, emailUser);
        valores.put(openHelper.NUM_COM, numCom);
        valores.put(openHelper.NUM_PUB, numPubl);
        valores.put(openHelper.MOTIVO, motivo);

        resultado = bd.insert(openHelper.TABELA_USER_DENUNCIA_COM,null, valores);
        fechar();

        if (resultado == -1)
            return "Erro ao denunciar";
        else
            return "Publicação denunciada";
    }

    public List<UserDenunciaCom> lerUsersDenunciamComs(){
        abrir();
        List<UserDenunciaCom> udc = new ArrayList<UserDenunciaCom>();
        Cursor cursor = bd.query(openHelper.TABELA_USER_DENUNCIA_COM, null, null,null,null,null,null);
        while(cursor.moveToNext()){
            UserDenunciaCom uc = new UserDenunciaCom();
            uc.setEmailUsuario(cursor.getString(0));
            uc.setNumeroComentario(cursor.getInt(1));
            uc.setNumeroPublicacao(cursor.getInt(2));
            uc.setMotivo(cursor.getString(3));

            udc.add(uc);
        }

        cursor.close();
        fechar();
        return udc;
    }
}