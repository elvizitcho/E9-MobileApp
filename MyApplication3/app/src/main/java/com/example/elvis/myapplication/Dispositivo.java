package com.example.elvis.myapplication;

public class Dispositivo {

    private String id;
    private String nome;
    private String ligado;

    public Dispositivo (String id, String nome, String ligado){
        this.id = id;
        this.nome = nome;
        this.ligado = ligado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLigado() {
        return ligado;
    }

    public void setLigado(String ligado) {
        this.ligado = ligado;
    }
}
