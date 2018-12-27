package com.example.aluno.cih.Model;

public class Comentario {
    private int numero;
    private String horaCom;
    private String dataCom;
    private String conteudo;
    private String emailUsuario;
    private int numeroPublicacao;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getHoraCom() {
        return horaCom;
    }

    public void setHoraCom(String horaCom) {
        this.horaCom = horaCom;
    }

    public String getDataCom() {
        return dataCom;
    }

    public void setDataCom(String dataCom) {
        this.dataCom = dataCom;
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

    public int getNumeroPublicacao() {
        return numeroPublicacao;
    }

    public void setNumeroPublicacao(int numeroPublicacao) {
        this.numeroPublicacao = numeroPublicacao;
    }
}