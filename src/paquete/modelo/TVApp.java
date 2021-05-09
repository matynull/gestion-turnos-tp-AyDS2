package paquete.modelo;

import paquete.util.Cliente;
import paquete.util.Paquete;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

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
        File archivo = new File ("cfg.txt");
        try {
            String ip = null;
            String puerto = null;
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            ip = br.readLine();
            puerto = br.readLine();
            fr.close();
            Socket socket = new Socket(ip,Integer.parseInt(puerto));
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
