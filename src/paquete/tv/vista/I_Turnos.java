package paquete.tv.vista;

import paquete.util.Cliente;

import java.util.ArrayList;
import java.util.LinkedList;

public interface I_Turnos {
    public void RefreshTable(LinkedList<Cliente> clientesAtendidos, ArrayList<Cliente> clientes);
}
