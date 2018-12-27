package com.example.aluno.cih.Model;

import android.graphics.Bitmap;

public class Usuario {
    private String email;
    private String senha;
    private String nome;
    private String perfil;
    private Bitmap imagem;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public Bitmap getImage() {
        return imagem;
    }

    public void setImage(Bitmap imagem) {
        this.imagem = imagem;
    }
}