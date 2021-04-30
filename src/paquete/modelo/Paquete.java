package paquete.modelo;

import java.util.ArrayList;

public class Paquete {
    private String port;
    private String ip;
    private int codigo; // 1 = manda dni | 2 = refrescar tv | 3 = lista a empleados
    private String dni;
    private ArrayList<Cliente> clientes;

    public Paquete(String dni) {
        this.codigo = 1;
        this.dni = dni;
    }

    public Paquete(int codigo, ArrayList<Cliente> clientes) {
        this.codigo = codigo;
        this.clientes = clientes;
    }

    public Paquete(int codigo) {
        this.codigo = codigo;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
