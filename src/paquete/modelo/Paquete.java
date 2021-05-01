package paquete.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;

public class Paquete implements Serializable {

    private int codigo; // 0 = ok | 1 = manda dni | 2 = refrescar tv | 3 = llama cliente | 4 = no hay clientes
    private String dni;
    private Cliente cliente;
    private Queue<Cliente> clientes;
    private Queue<Cliente> clientesSiendoAtendidos;
    private int box;

    public Paquete() {
    }

    public Paquete(String dni) {
        this.codigo = 1;
        this.dni = dni;
    }

    public Paquete(int codigo) {
        this.codigo = codigo;
    }

    public Queue<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Queue<Cliente> clientes) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Queue<Cliente> getClientesSiendoAtendidos() {
        return clientesSiendoAtendidos;
    }

    public void setClientesSiendoAtendidos(Queue<Cliente> clientesSiendoAtendidos) {
        this.clientesSiendoAtendidos = clientesSiendoAtendidos;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }
}
