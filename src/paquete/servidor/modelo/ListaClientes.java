package paquete.servidor.modelo;

import paquete.servidor.estados.I_State;
import paquete.servidor.estados.OrdenLlegadaState;
import paquete.util.Cliente;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaClientes implements Serializable {
    private I_State estado;
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public ListaClientes() {
        this.estado= new OrdenLlegadaState(this);
    }

    public void ordenar(){
        this.estado.ordenar(this.clientes);
    }

    public void cambiarDescente(){
        this.estado.cambiarDescente();
    }

    public void cambiarAscendente(){
        estado.cambiarAscente();
    }

    public void cambiarOrdenLlegada(){
        estado.cambiarOrdenLlegada();
    }

    public void cambiarPrioridad(){
        estado.cambiarPrioridad();
    }


    public I_State getEstado() {
        return estado;
    }

    public Cliente poll(){
        return clientes.remove(0);
    }

    public void add(Cliente cliente){
        clientes.add(cliente);
        estado.ordenar(clientes);
    }

    public void setEstado(I_State state){
        this.estado = state;
    }

    public boolean isEmpty(){
        return clientes.isEmpty();
    }

    public ArrayList<Cliente> getList(){
        return clientes;
    }
}
