package com.example.aluno.cih.Model;

public class UserDenunciaCom {
    private String emailUsuario;
    private int numeroComentario;
    private int numeroPublicacao;
    private String motivo;

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public int getNumeroComentario() {
        return numeroComentario;
    }

    public void setNumeroComentario(int numeroComentario) {
        this.numeroComentario = numeroComentario;
    }

    public int getNumeroPublicacao() {
        return numeroPublicacao;
    }

    public void setNumeroPublicacao(int numeroPublicacao) {
        this.numeroPublicacao = numeroPublicacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}