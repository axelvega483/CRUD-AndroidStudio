package com.example.crud;

public class Contacto {
    int id;
    int edad;
    String nombre;
    String telefono;
    String email;


    public Contacto() {
    }

    public Contacto( String nombre,int edad, String telefono, String email) {
        this.edad = edad;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public Contacto(int id, String nombre, int edad, String email, String telefono) {
        this.id = id;
        this.email = email;
        this.telefono = telefono;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


}
