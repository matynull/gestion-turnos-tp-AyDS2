package paquete.servidor.estados;
import paquete.util.*;

import java.io.Serializable;
import java.util.ArrayList;

public interface I_State extends Serializable {

    public void ordenar(ArrayList<Cliente> list);
    public void cambiarDescente();
    public void cambiarAscente();
    public void cambiarPrioridad();
    public void cambiarOrdenLlegada();

}
