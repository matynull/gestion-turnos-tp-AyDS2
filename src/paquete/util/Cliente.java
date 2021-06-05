package paquete.util;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String dni,nombre;
    private int box,categoria;
    private boolean hasBox=false;

    public Cliente(){
    }

    public Cliente(String dni){
        this.dni=dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    @Override
    public String toString() {
        String string=dni;
        if(hasBox==true){
            string+="                                                                   "+box;
        }
        return string;
    }

    public void setHasBox() {
        this.hasBox = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
