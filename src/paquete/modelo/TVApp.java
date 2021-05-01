package paquete.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

public class TVApp {

    private static TVApp TVApp;
    private Queue<Cliente> clientes;
    private Queue<Cliente> clientesSiendoAtendidos;

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

    public Queue<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Queue<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Queue<Cliente> getClientesSiendoAtendidos() {
        return clientesSiendoAtendidos;
    }

    public void setClientesSiendoAtendidos(Queue<Cliente> clientesSiendoAtendidos) {
        this.clientesSiendoAtendidos = clientesSiendoAtendidos;
    }
}