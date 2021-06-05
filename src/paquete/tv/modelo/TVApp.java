package paquete.tv.modelo;

import paquete.util.Cliente;
import paquete.util.Paquete;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

public class TVApp {

    private static TVApp TVApp;
    private LinkedList<Cliente> clientes;
    private LinkedList<Cliente> clientesSiendoAtendidos;
    private boolean principal=true;
    private int intentos=0;

    private TVApp(){

    }

    public static TVApp getInstance() {
        if(TVApp == null)
            TVApp = new TVApp();
        return TVApp;
    }

    public void refrescarTV(){
        File archivo;
        if(principal)
            archivo = new File ("cfg.txt");
        else
            archivo = new File("cfg2.txt");
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
            socket.setSoTimeout(2000);
            os.writeObject(new Paquete(2));
            Paquete paqueteRta = (Paquete) is.readObject();
            if(paqueteRta.getCodigo()==404)
                throw new SocketTimeoutException();
            this.setClientes(paqueteRta.getClientes());
            this.setClientesSiendoAtendidos(paqueteRta.getClientesSiendoAtendidos());
            socket.close();
            intentos=0;
        }catch(SocketTimeoutException | ConnectException e){
            principal=!principal;
            intentos++;
            if(intentos>2)
                return;
            refrescarTV();
        }
        catch (IOException | ClassNotFoundException e) {
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
