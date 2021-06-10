package paquete.servidor.estados;

import paquete.servidor.factory.StateFactory;
import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;

import java.util.ArrayList;
import java.util.Comparator;

public class DescendenteState implements I_State{

    ListaClientes clientes;
    public DescendenteState(ListaClientes clientes){
        this.clientes=clientes;
    }
    @Override
    public void ordenar(ArrayList<Cliente> list) {
        list.sort(Comparator.comparing(Cliente::getDni).reversed());
    }

    public void cambiarDescente(){

    }
    public void cambiarAscente(){

        clientes.setEstado(StateFactory.getState("Ascendente",clientes));
    }
    public void cambiarPrioridad(){
        clientes.setEstado(StateFactory.getState("Prioridad",clientes));
    }
    public void cambiarOrdenLlegada(){
        clientes.setEstado(StateFactory.getState("Orden de Llegada",clientes));
    }
}
