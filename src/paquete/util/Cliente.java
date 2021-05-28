package paquete.util;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String dni;
    private int box;
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
}
