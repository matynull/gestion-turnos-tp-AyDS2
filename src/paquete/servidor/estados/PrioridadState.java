package paquete.servidor.estados;

import paquete.servidor.factory.StateFactory;
import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;

import java.util.ArrayList;
import java.util.Comparator;

public class PrioridadState implements I_State{
    ListaClientes clientes;

    public PrioridadState(ListaClientes clientes){
        this.clientes = clientes;
    }

    public void ordenar(ArrayList<Cliente> list){
        list.sort(Comparator.comparingInt(Cliente::getCategoria));
    }
    public void cambiarDescente(){
        clientes.setEstado(StateFactory.getState("Descendente",clientes));
    }
    public void cambiarAscente(){
        clientes.setEstado(StateFactory.getState("Ascendente",clientes));
    }
    public void cambiarPrioridad(){
    }
    public void cambiarOrdenLlegada(){
        clientes.setEstado(StateFactory.getState("Orden de Llegada",clientes));
    }
}
