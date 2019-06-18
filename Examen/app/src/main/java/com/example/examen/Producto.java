package com.example.examen;

public class Producto {
    String nombre;
    float precio;
    String fecha;
    boolean status;


    public Producto(String nombre, float precio, String fecha, boolean status) {
        this.nombre = nombre;
        this.precio = precio;
        this.fecha = fecha;
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
