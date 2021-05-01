package paquete.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Servidor extends Thread {

    private ArrayList<Empleado> empleados;
    private Queue<Cliente> clientes;
    private Queue<Cliente> clientesSiendoAtendidos;


    public static void main(String[] args){
        Servidor server = new Servidor();
        server.start();
    }
    public Servidor() {
        this.empleados = new ArrayList<Empleado>();
        this.clientes = new LinkedList<Cliente>();
        this.clientesSiendoAtendidos = new LinkedList<>();
        }

    @Override
    public void run() {
        try {
            System.out.println("iniciando");
            ServerSocket serverSocket = new ServerSocket(9000);
            while(true){
                System.out.println("esperando");
                Socket socket = serverSocket.accept();
                System.out.println("conecto");
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                Paquete paqueteEntrada = (Paquete) is.readObject();
                this.leePaquete(paqueteEntrada,os);

                socket.close();

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void leePaquete(Paquete paquete, ObjectOutputStream os) throws IOException {
        int caso = paquete.getCodigo();
        if(caso == 1)
            this.clientes.add(new Cliente(paquete.getDni()));
        os.writeObject(this.armaPaquete(paquete));
    }

    private Paquete armaPaquete(Paquete paquete){
        Paquete paqueteRespuesta = new Paquete();
        int codigo = paquete.getCodigo();
        switch (codigo){
            case 1:
                paqueteRespuesta.setCodigo(0);
                break;
            case 2:
                paqueteRespuesta.setCodigo(0);
                paqueteRespuesta.setClientesSiendoAtendidos(this.clientesSiendoAtendidos); // devuelve clientes siendo atendidos a TV
                paqueteRespuesta.setClientes(this.clientes); // devuelve clientes a tv
                break;
            case 3:
                    if(this.clientes.isEmpty()){
                        paqueteRespuesta.setCodigo(4);
                    }else{
                        Cliente clienteAtendido = this.clientes.poll();
                        paqueteRespuesta.setCodigo(0);
                        clienteAtendido.setBox(paquete.getBox());
                        this.clientesSiendoAtendidos.add(clienteAtendido);
                        paqueteRespuesta.setCliente(clienteAtendido); // devuelve el cliente al empleado
                    }
                break;
        }
            return paqueteRespuesta;
    }

}

