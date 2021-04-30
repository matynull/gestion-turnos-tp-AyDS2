package paquete.modelo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Servidor extends Thread {

    private ArrayList<Empleado> empleados;
    private Queue<Cliente> clientes;

    public Servidor() {
        this.empleados = new ArrayList<Empleado>();
        this.clientes = new LinkedList<Cliente>();

        }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while(true){
                Socket socket = serverSocket.accept();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

