package paquete.tv.modelo;

import paquete.servidor.modelo.ListaClientes;
import paquete.util.Cliente;
import paquete.util.Paquete;
import paquete.util.Aplicacion;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

public class TVApp extends Aplicacion {

    private static TVApp TVApp;
    private ListaClientes clientes;
    private LinkedList<Cliente> clientesSiendoAtendidos;
    private boolean principal=true;
    private int intentos=0;
    private String modo= null;

    private TVApp(){

    }

    public static TVApp getInstance() {
        if(TVApp == null)
            TVApp = new TVApp();
        return TVApp;
    }

    @Override
    public void ejecutarApp(){
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
            modo = br.readLine();
            fr.close();
            Socket socket = new Socket(ip,Integer.parseInt(puerto));
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(2000);
            os.writeObject(armarPaquete());
            Paquete paqueteRta = (Paquete) is.readObject();
            if(paqueteRta.getCodigo()==404)
                throw new SocketTimeoutException();
            administrarPaquete(paqueteRta);
            socket.close();
            intentos=0;
        }catch(SocketTimeoutException | ConnectException e){
            principal=!principal;
            intentos++;
            if(intentos>2)
                return;
            ejecutarApp();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paquete armarPaquete(){
        Paquete paquete = new Paquete(2);
        paquete.setModo(modo);
        return paquete;
    }

    @Override
    public void administrarPaquete(Paquete paqueteRta){
        this.setClientes(paqueteRta.getClientes());
        this.setClientesSiendoAtendidos(paqueteRta.getClientesSiendoAtendidos());
    }

    public ListaClientes getClientes() {
        return clientes;
    }

    public void setClientes(ListaClientes clientes) {
        this.clientes = clientes;
    }

    public LinkedList<Cliente> getClientesSiendoAtendidos() {
        return clientesSiendoAtendidos;
    }

    public void setClientesSiendoAtendidos(LinkedList<Cliente> clientesSiendoAtendidos) {
        this.clientesSiendoAtendidos = clientesSiendoAtendidos;
    }
}
