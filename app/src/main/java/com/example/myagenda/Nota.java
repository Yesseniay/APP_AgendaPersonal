package com.example.myagenda;


public class Nota {
    private int id;
    private String titulo;
    private String texto;

    public Nota(int id, String titulo, String texto) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTexto() { return texto; }
}
