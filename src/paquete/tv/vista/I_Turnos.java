package paquete.tv.vista;

import paquete.util.Cliente;

import java.util.LinkedList;

public interface I_Turnos {
    public void RefreshTable(LinkedList<Cliente> clientesAtendidos, LinkedList<Cliente> clientes);
}
