package paquete.servidor.estados;

import paquete.servidor.factory.StateFactory;
import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;

import java.util.ArrayList;
import java.util.Comparator;

public class AscendenteState implements I_State{



    ListaClientes clientes;

    public AscendenteState(ListaClientes clientes){
        this.clientes=clientes;
    }
    @Override
    public void ordenar(ArrayList<Cliente> list) {
        list.sort(Comparator.comparing(Cliente::getDni));
    }

    public void cambiarDescente(){
        clientes.setEstado(StateFactory.getState("Descendete",clientes));
    }
    public void cambiarAscente(){
    }
    public void cambiarPrioridad(){
        clientes.setEstado(StateFactory.getState("Prioridad",clientes));
    }
    public void cambiarOrdenLlegada(){
        clientes.setEstado(StateFactory.getState("Orden de Llegada",clientes));
    }
}
