package com.example.draft_tfg;

public class ListaLogin {
    int id;
    String Nombre, Apellido, Usuario, Contrasena;

    public ListaLogin() {
    }

    public ListaLogin(String nombre, String apellido, String usuario, String contrasena ) {
        Nombre = nombre;
        Apellido = apellido;
        Usuario = usuario;
        Contrasena = contrasena;

    }
    public boolean isNull(){
        if (Usuario.equals("")&&Contrasena.equals("")){
            return  false;
        }else {
            return true;
        }
    }
    @Override
    public String toString() {
        return "ListaLogin{" +
                "Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

}
