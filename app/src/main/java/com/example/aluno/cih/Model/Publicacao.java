package com.example.aluno.cih.Model;

public class Publicacao {
    private int numero;
    private String horaPub;
    private String dataPub;
    private String conteudo;
    private String emailUsuario;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getHoraPub() {
        return horaPub;
    }

    public void setHoraPub(String horaPub) {
        this.horaPub = horaPub;
    }

    public String getDataPub() {
        return dataPub;
    }

    public void setDataPub(String dataPub) {
        this.dataPub = dataPub;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}