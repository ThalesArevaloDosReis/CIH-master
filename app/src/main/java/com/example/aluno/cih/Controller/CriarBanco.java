package com.example.aluno.cih.Controller;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CriarBanco extends SQLiteOpenHelper{
    protected static final int VERSAO = 4;
    //Nome do BD
    protected static final String NOME_BANCO = "bd_cih.db";
    //Para tabela Usuario
    protected static final String TABELA_USER = "Usuario";
    protected static final String EMAIL = "email";
    protected static final String SENHA = "senha";
    protected static final String NOME = "nome";
    protected static final String PERFIL = "perfil";
    protected static final String IMAGEM = "imagem";
    //Para tabela Publicacao
    protected static final String TABELA_PUBLIC = "Publicacao";
    protected static final String NUMERO = "numero"; // Pode ser tanto de Publicação, quanto de Comentario
    protected static final String HORA_PUB = "horaPub";
    protected static final String DATA_PUB = "dataPub";
    protected static final String CONTEUDO = "conteudo"; // Pode ser tanto de Publicação, quanto de Comentario
    protected static final String EMAIL_USER = "emailUsuario"; // Pode ser tanto de Publicação, quanto de Comentario, UsuarioCurteComentario e UsuarioDenunciaComentario
    //Para tabela Comentario
    protected static final String TABELA_COM = "Comentario";
    protected static final String HORA_COM = "horaCom";
    protected static final String DATA_COM = "dataCom";
    protected static final String NUM_PUB = "numPub"; // Pode ser tanto de Comentario, quanto de UsuarioCurteComentario e UsuarioDenunciaComentario
    //Para tabela UsuarioCurteComentario
    protected static final String TABELA_USER_CURTE_COM = "UsuarioCurteComentario";
    protected static final String NUM_COM = "numCom"; // Pode ser tanto de UsuarioCurteComentario, quanto de UsuarioDenunciaComentario
    protected static final String VALOR = "valor";
    //Para tabela UsuarioDenunciaComentario
    protected static final String TABELA_USER_DENUNCIA_COM = "UsuarioDenunciaComentario";
    protected static final String MOTIVO = "motivo";

    public CriarBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String comando = "CREATE TABLE " + TABELA_USER + "(" + EMAIL + " TEXT PRIMARY KEY, " + NOME + " TEXT NOT NULL, " +  SENHA + " TEXT NOT NULL, " + PERFIL + " TEXT NOT NULL, " + IMAGEM + " BLOB NOT NULL)";
        db.execSQL(comando);
        comando = "CREATE TABLE " + TABELA_PUBLIC + "(" + NUMERO + " INT PRIMARY KEY, " + HORA_PUB + " TEXT NOT NULL, " +  DATA_PUB + " TEXT NOT NULL, " + CONTEUDO + " TEXT NOT NULL, " + EMAIL_USER + " TEXT NOT NULL, FOREIGN KEY (" + EMAIL_USER + ") REFERENCES " + TABELA_USER + "(" + EMAIL + "))";
        db.execSQL(comando);
        comando = "CREATE TABLE " + TABELA_COM + "(" + NUMERO + " INT PRIMARY KEY, " + HORA_COM + " TEXT NOT NULL, " +  DATA_COM + " TEXT NOT NULL, " + CONTEUDO + " TEXT NOT NULL, " + EMAIL_USER + " TEXT NOT NULL, " + NUM_PUB + " INT NOT NULL, FOREIGN KEY (" + EMAIL_USER + ") REFERENCES " + TABELA_USER + "(" + EMAIL + "), FOREIGN KEY (" + NUM_PUB + ") REFERENCES " + TABELA_PUBLIC + "(" + NUMERO + "))";
        db.execSQL(comando);
        comando = "CREATE TABLE " + TABELA_USER_CURTE_COM + "(" + EMAIL_USER + " TEXT NOT NULL, " + NUM_COM + " INT NOT NULL, PRIMARY KEY (" + EMAIL_USER + "," + NUM_COM + "), FOREIGN KEY (" + EMAIL_USER + ") REFERENCES " + TABELA_USER + "(" + EMAIL + "), FOREIGN KEY (" + NUM_COM + ") REFERENCES " + TABELA_COM + "(" + NUMERO + "))";
        db.execSQL(comando);
        comando = "CREATE TABLE " + TABELA_USER_DENUNCIA_COM + "(" + EMAIL_USER + " TEXT NOT NULL, " + NUM_COM + " INT NOT NULL, " +  MOTIVO + " TEXT NOT NULL, PRIMARY KEY (" + EMAIL_USER + "," + NUM_COM + "), FOREIGN KEY (" + EMAIL_USER + ") REFERENCES " + TABELA_USER + "(" + EMAIL + "), FOREIGN KEY (" + NUM_COM + ") REFERENCES " + TABELA_COM + "(" + NUMERO + "))";
        db.execSQL(comando);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}