package paquete.servidor.modelo;


import paquete.servidor.interfaces.I_BuscaCliente;
import paquete.servidor.interfaces.I_ColaDeTurnos;
import paquete.servidor.interfaces.I_SiguienteCliente;
import paquete.servidor.interfaces.I_Sincronizacion;
import paquete.util.Cliente;
import paquete.util.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Servidor extends Thread implements I_ColaDeTurnos, I_SiguienteCliente, I_Sincronizacion, I_BuscaCliente {

    private boolean esPrincipal;
    private ListaClientes clientes;
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
        this.clientes = new ListaClientes();
        this.clientes.cambiarOrdenLlegada();
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
            this.sincronizoServidores(ipRespaldo, puertoRespaldo);
            while (true) {
                if (this.esPrincipal) {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    Paquete paqueteEntrada = (Paquete) is.readObject();
                    os.writeObject(this.armaPaquete(paqueteEntrada));
                    socket.close();
                } else {
                    serverSocket.setSoTimeout(2000);
                    while (!this.esPrincipal) {
                        leoServidor(serverSocket);
                    }
                    serverSocket.setSoTimeout(0);
                    this.sincronizoServidores(ipRespaldo, puertoRespaldo);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Paquete armaPaquete(Paquete paquete) {
        Paquete paqueteRespuesta = new Paquete();
        int codigo = paquete.getCodigo();
        switch (codigo) {
            case 1:
                Cliente cliente = buscaCliente(paquete.getDni());
                if (cliente != null) {
                    System.out.println("Se registro cliente");
                    log("Se registro el cliente " + cliente.getNombre());
                    paqueteRespuesta.setCodigo(0);
                    this.clientes.add(cliente);
                } else {
                    paqueteRespuesta.setCodigo(405);
                }
                break;
            case 2:
                ColaDeTurnos(paqueteRespuesta);
                if(paquete.getModo()!=null)
                    cambiarModo(paquete.getModo());
                break;
            case 3:
                siguienteCliente(paquete, paqueteRespuesta);
                break;
            case 100:
                paqueteRespuesta.setCodigo(0);
                break;
        }
        return paqueteRespuesta;
    }

    @Override
    public void ColaDeTurnos(Paquete paqueteRespuesta) {
        paqueteRespuesta.setCodigo(0);
        paqueteRespuesta.setClientesSiendoAtendidos(this.clientesSiendoAtendidos); // devuelve clientes siendo atendidos a TV
        paqueteRespuesta.setClientes(this.clientes); // devuelve clientes a tv
    }

    @Override
    public void siguienteCliente(Paquete paquete, Paquete paqueteRespuesta) {
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
                System.out.println("Se atendio cliente");
                log("Se atendio al cliente " + clienteAtendido.getNombre() + ". En el puesto" + paquete.getBox());
            }
        }
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

    @Override
    public void mandarUpdate(String ip, int puerto) {
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

    @Override
    public void leoServidor(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Paquete paquete = (Paquete) is.readObject();
            if (paquete.getCodigo() == 50) {
                this.clientes = paquete.getClientes();
                this.clientesSiendoAtendidos = paquete.getClientesSiendoAtendidos();
            } else {
                if (paquete.getCodigo() == 100)
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

    @Override
    public void sincronizoServidores(String ip, int puerto) {
        if (this.esPrincipal) {
            new Thread(() -> {
                try {
                    while (true) {
                        this.mandarUpdate(ip, puerto);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public Cliente buscaCliente(String dni) {
        Cliente cliente = null;
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = null;
        try {
            archivo = new File("clientes.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while (((linea = br.readLine()) != null) && !dni.equals(linea.substring(0, linea.indexOf(' ')))) ;

            fr.close();
            if (linea != null) {
                cliente = new Cliente(dni);
                String aux = linea.substring(linea.indexOf(' '));
                cliente.setNombre(aux.substring(0, aux.length()-2));
                cliente.setCategoria(Integer.parseInt(aux.charAt(aux.length()-1)+""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    private void log(String s) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        sb.append("[" + sdf.format(ts) + "] " + s);
        FileWriter archivo = null;
        PrintWriter pw = null;
        try {
            archivo = new FileWriter("log.txt", true);
            pw = new PrintWriter(archivo);
            pw.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (archivo != null)
                    archivo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void cambiarModo (String s){
        switch (s.toCharArray()[0]) {
            case 'A':
                clientes.cambiarAscendente();
                break;
            case 'D':
                clientes.cambiarDescente();
                break;
            case 'O':clientes.cambiarOrdenLlegada();
            break;
            case 'P':clientes.cambiarPrioridad();
        }
        clientes.ordenar();

    }
}

