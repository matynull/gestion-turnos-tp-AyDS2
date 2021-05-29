package paquete.servidor;


import paquete.util.Cliente;
import paquete.util.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Scanner;

public class Servidor extends Thread {

    private boolean esPrincipal;
    private LinkedList<Cliente> clientes;
    private LinkedList<Cliente> clientesSiendoAtendidos;

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(int esPrincipal) {
        if (esPrincipal == 0)
            this.esPrincipal = true;
        else
            this.esPrincipal = false;
    }

    public static void main(String[] args) {
        Servidor server = new Servidor();
        server.start();
    }

    public Servidor() {
        this.clientes = new LinkedList<Cliente>();
        this.clientesSiendoAtendidos = new LinkedList<>();
    }

    @Override
    public void run() {
        String ipRespaldo;
        int puertoRespaldo;
        try {
            Scanner reader = new Scanner(System.in);
            System.out.println("Ingrese puerto a utilizar: ");
            ServerSocket serverSocket = new ServerSocket(reader.nextInt());
            System.out.println("Server principal -> 0  / Server respaldo -> 1");
            this.setEsPrincipal(reader.nextInt());
            System.out.println("Ingrese IP del otro Servidor");
            ipRespaldo = reader.next();
            System.out.println("Ingrese el puerto del mismo");
            puertoRespaldo = reader.nextInt();

            System.out.println("Conectando...");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Conectado");
            this.sincronizoServidores(ipRespaldo,puertoRespaldo);
            while (true) {
                if (this.esPrincipal) {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    Paquete paqueteEntrada = (Paquete) is.readObject();
                    this.leePaquete(paqueteEntrada, os);
                    socket.close();
                } else {
                    serverSocket.setSoTimeout(2000);
                    while (!this.esPrincipal) {
                        leoServidor(serverSocket);
                    }
                    serverSocket.setSoTimeout(0);
                    this.sincronizoServidores(ipRespaldo,puertoRespaldo);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void leePaquete(Paquete paquete, ObjectOutputStream os) throws IOException {
        int caso = paquete.getCodigo();
        if (caso == 1)
            this.clientes.add(new Cliente(paquete.getDni()));
        os.writeObject(this.armaPaquete(paquete));
    }

    private Paquete armaPaquete(Paquete paquete) {
        Paquete paqueteRespuesta = new Paquete();
        int codigo = paquete.getCodigo();
        switch (codigo) {
            case 1:
                System.out.println("Se registro un cliente");
                paqueteRespuesta.setCodigo(0);
                break;
            case 2:
                paqueteRespuesta.setCodigo(0);
                paqueteRespuesta.setClientesSiendoAtendidos(this.clientesSiendoAtendidos); // devuelve clientes siendo atendidos a TV
                paqueteRespuesta.setClientes(this.clientes); // devuelve clientes a tv
                break;
            case 3:
                System.out.println("Se atendio a un cliente");
                if (paquete.getCliente() != null) {
                    removerCliente(paquete.getCliente().getBox());
                }
                if (this.clientes.isEmpty()) {
                    paqueteRespuesta.setCodigo(4);
                } else {
                    synchronized (clientes) {
                        Cliente clienteAtendido = this.clientes.poll();
                        paqueteRespuesta.setCodigo(0);
                        clienteAtendido.setBox(paquete.getBox());
                        clienteAtendido.setHasBox();
                        this.clientesSiendoAtendidos.add(clienteAtendido);
                        paqueteRespuesta.setCliente(clienteAtendido); // devuelve el cliente al empleado
                    }
                }
                break;
            case 100:
                paqueteRespuesta.setCodigo(0);
                break;
        }
        return paqueteRespuesta;
    }

    private void removerCliente(int box) {
        synchronized (clientesSiendoAtendidos) {
            Cliente aux = null;
            for (Cliente c : clientesSiendoAtendidos) {
                if (c.getBox() == box) {
                    aux = c;
                }
            }
            if (aux != null)
                clientesSiendoAtendidos.remove(aux);
        }
    }

    private void mandarUpdate(String ip, int puerto) {
        try {
            Socket socket = new Socket(ip, puerto);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            Paquete paquete = armaPaquete(new Paquete(2));
            paquete.setCodigo(50);
            os.writeObject(paquete);
        } catch (IOException e) {
            //System.out.println("El servidor secundario esta caido!!!");
        }
    }

    private void leoServidor(ServerSocket serverSocket) {
        try {
            Socket socket =serverSocket.accept();
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Paquete paquete = (Paquete) is.readObject();
            if(paquete.getCodigo()==50) {
                this.clientes = paquete.getClientes();
                this.clientesSiendoAtendidos = paquete.getClientesSiendoAtendidos();
            }
            else{
                if(paquete.getCodigo()==100)
                    os.writeObject(armaPaquete(paquete));
                else {
                    paquete.setCodigo(404);
                    os.writeObject(paquete);
                }
            }
        } catch (SocketTimeoutException e) {
            this.esPrincipal = true;
            System.out.println("No se pudo establecer una conexion con el servidor principal, cambiando roles...");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    private void sincronizoServidores(String ip, int puerto){
        if(this.esPrincipal) {
            new Thread(() -> {
                try {
                    while(true) {
                        this.mandarUpdate(ip, puerto);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}

