package paquete.util;

public class Empleado {
    private String nombre;
    private int box;

    public Empleado() {
    }

    public Empleado(String nombre, int box) {
        this.nombre = nombre;
        this.box = box;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }
}
