package paquete.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class TVApp {

    private static TVApp TVApp;
    private LinkedList<Cliente> clientes;
    private LinkedList<Cliente> clientesSiendoAtendidos;

    private TVApp(){

    }

    public static TVApp getInstance() {
        if(TVApp == null)
            TVApp = new TVApp();
        return TVApp;
    }

    public void refrescarTV(){
        try {
            Socket socket = new Socket("localhost",9000);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(new Paquete(2));
            Paquete paqueteRta = (Paquete) is.readObject();
            this.setClientes(paqueteRta.getClientes());
            this.setClientesSiendoAtendidos(paqueteRta.getClientesSiendoAtendidos());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(LinkedList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public LinkedList<Cliente> getClientesSiendoAtendidos() {
        return clientesSiendoAtendidos;
    }

    public void setClientesSiendoAtendidos(LinkedList<Cliente> clientesSiendoAtendidos) {
        this.clientesSiendoAtendidos = clientesSiendoAtendidos;
    }
}
