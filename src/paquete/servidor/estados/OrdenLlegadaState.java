package paquete.servidor.estados;

import paquete.servidor.factory.StateFactory;
import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;
import java.util.*;

public class OrdenLlegadaState implements I_State{

    ListaClientes clientes;

    public OrdenLlegadaState(ListaClientes clientes) {
        this.clientes = clientes;
    }

    public void ordenar(ArrayList<Cliente> list){
        list.sort(Comparator.comparingInt(Cliente::getnroLlegada));
    }

    public void cambiarDescente(){
        clientes.setEstado(StateFactory.getState("Descendente",clientes));
    }
    public void cambiarAscente(){
        clientes.setEstado(StateFactory.getState("Ascendente",clientes));
    }
    public void cambiarPrioridad(){
        clientes.setEstado(StateFactory.getState("Prioridad",clientes));
    }
    public void cambiarOrdenLlegada(){
    }
}
