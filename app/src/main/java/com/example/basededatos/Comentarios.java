package com.example.basededatos;

public class Comentarios {
    String nombre, texto;

    public Comentarios(String nombre, String texto) {
        this.nombre = nombre;
        this.texto = texto;
    }

    public Comentarios() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
