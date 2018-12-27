package com.example.aluno.cih.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.aluno.cih.Model.Usuario;
import com.google.firebase.FirebaseApp;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;


public class ListaUsuarios {
    private SQLiteDatabase bd;
    private CriarBanco openHelper;

    public ListaUsuarios(Context contexto ){
        openHelper = new CriarBanco(contexto);
    }

    public void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    public void fechar(){
        bd.close();
    }

    public String addAoBD(String email, String nome, String senha, String perfil,  byte[] imagem){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.EMAIL, email);
        valores.put(openHelper.SENHA, senha);
        valores.put(openHelper.NOME, nome);
        valores.put(openHelper.PERFIL, perfil);
        valores.put(openHelper.IMAGEM, imagem);

        resultado = bd.insert(openHelper.TABELA_USER,null, valores);
        fechar();

        if (resultado == -1)
            return "Erro ao cadastrar";
        else
            return "Cadastro realizado com sucesso";
    }

    public void removeDoBD(String email){
        bd.delete(openHelper.TABELA_USER,"" + openHelper.EMAIL + " = '" + email + "'",null);
    }

    public String alterarUser(String email, String nome, String perfil, byte[] imagem){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.NOME, nome);
        valores.put(openHelper.PERFIL, perfil);
        valores.put(openHelper.IMAGEM, imagem);

        resultado = bd.update(openHelper.TABELA_USER, valores, "" + openHelper.EMAIL + " = '" + email + "'",null);
        fechar();

        if (resultado == -1)
            return "Erro ao editar o perfil";
        else
            return "Perfil editado com sucesso";
    }

    public String alterarSenha(String email, String senha){
        abrir();
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(openHelper.SENHA, senha);

        resultado = bd.update(openHelper.TABELA_USER, valores, "" + openHelper.EMAIL + " = '" + email + "'",null);
        fechar();

        if (resultado == -1)
            return "Erro ao alterar senha";
        else
            return "Senha alterada com sucesso";
    }

    public List<Usuario> lerUsuarios(){
        abrir();
        List<Usuario> usuario = new ArrayList<Usuario>();
        Cursor cursor = bd.query(openHelper.TABELA_USER, null, null,null,null,null,null);
        while(cursor.moveToNext()){
            Usuario u = new Usuario();
            u.setEmail(cursor.getString(0));
            u.setNome(cursor.getString(1));
            u.setSenha(cursor.getString(2));
            u.setPerfil(cursor.getString(3));
            byte[] imageByteArray = cursor.getBlob(4);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            u.setImage(theImage);
            usuario.add(u);
        }

        cursor.close();
        fechar();
        return usuario;
    }
}