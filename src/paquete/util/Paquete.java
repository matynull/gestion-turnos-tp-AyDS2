package paquete.util;

import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;

import java.io.Serializable;
import java.util.LinkedList;

public class Paquete implements Serializable {

    private int codigo; // 0 = ok | 1 = manda dni | 2 = refrescar tv | 3 = llama cliente | 4 = no hay clientes
    private String dni,modo;
    private Cliente cliente;
    private ListaClientes clientes;
    private LinkedList<Cliente> clientesSiendoAtendidos;
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

    public Paquete(int codigo, int box, Cliente cliente){
        this.codigo = codigo;
        this.box=box;
        this.cliente=cliente;
    }

    public ListaClientes getClientes() {
        return clientes;
    }

    public void setClientes(ListaClientes clientes) {
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

    public LinkedList<Cliente> getClientesSiendoAtendidos() {
        return clientesSiendoAtendidos;
    }

    public void setClientesSiendoAtendidos(LinkedList<Cliente> clientesSiendoAtendidos) {
        this.clientesSiendoAtendidos = clientesSiendoAtendidos;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public void setModo(String s){
        modo = s;
    }
    public String getModo(){
        return modo;
    }
}
