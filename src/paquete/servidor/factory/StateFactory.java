package paquete.servidor.factory;

import paquete.servidor.estados.AscendenteState;
import paquete.servidor.estados.*;
import paquete.servidor.modelo.ListaClientes;
import sun.security.krb5.internal.crypto.Des;

public class StateFactory {

    public static I_State getState(String state, ListaClientes lista) {
        if(state.equals("Ascendente"))
            return new AscendenteState(lista);
        else
            if(state.equals("Descendente"))
                return new DescendenteState(lista);
            else
                if(state.equals("Orden de Llegada"))
                    return new OrdenLlegadaState(lista);
                else
                    if(state.equals("Prioridad"))
                        return new PrioridadState(lista);
        return null;
    }
}
