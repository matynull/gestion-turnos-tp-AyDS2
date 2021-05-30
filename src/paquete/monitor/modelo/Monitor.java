package paquete.monitor.modelo;


import paquete.util.Paquete;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Monitor implements I_PingEcho{

    public static void main(String[] args) {
        Monitor monitor = Monitor.getInstance();

        File archivo1 =  new File("cfg.txt");
        File archivo2 =  new File("cfg2.txt");

        try {
            FileReader fr1 = new FileReader(archivo1);
            FileReader fr2 = new FileReader(archivo2);
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);

            Monitor.getInstance().hiloMonitoreo(br1.readLine(), Integer.parseInt(br1.readLine()));
            Monitor.getInstance().hiloMonitoreo(br2.readLine(), Integer.parseInt(br2.readLine()));
            fr1.close();
            fr2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Monitor instance = null;

    public static Monitor getInstance(){
        if (instance == null) {
            instance = new Monitor();
        }
        return instance;
    }

    private Monitor() {

    }

    public Paquete armaPaquete() {
        return new Paquete(100);
    }


    public void hiloMonitoreo(String ipServer, int puertoServer) {
        new Thread(() -> {
                Socket socket = null;
                try {
                    while(true) {
                        Thread.sleep(10000);
                        //System.out.println("\033[H\033[2J");
                        socket = new Socket(ipServer, puertoServer);
                        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                        socket.setSoTimeout(2000);
                        os.writeObject(this.armaPaquete());
                        Paquete paqueteRespuesta = (Paquete) is.readObject();
                        if (paqueteRespuesta.getCodigo() == 0)
                            System.out.println("El servidor " + ipServer + " con el puerto en " + puertoServer + ". Esta funcionando correctamente");
                        else
                            System.out.println("El servidor " + ipServer + " con el puerto en " + puertoServer + ". No esta en funcionamiento");
                    }

                } catch (SocketTimeoutException | ConnectException e) {
                    System.out.println("El servidor " + ipServer + " con el puerto en " + puertoServer + ". No esta en funcionamiento");
                    this.hiloMonitoreo(ipServer, puertoServer);
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
            }
        }).start();
    }
}
