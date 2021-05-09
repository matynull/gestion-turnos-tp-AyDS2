package paquete.modelo;

import paquete.util.Paquete;

import java.io.*;
import java.net.Socket;

public class TotemApp {

    private static TotemApp totemApp;
    private int verificiacion=1;

    private TotemApp() {
    }

    public void enviarPaquete(String dni){
        File archivo = new File ("archivo.txt");
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
            os.writeObject(armaPaquete(dni));
            Paquete rta = (Paquete) is.readObject();
            Verificacion(rta.getCodigo());
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Paquete armaPaquete(String dni){
        return new Paquete(dni);
    }

    public void Verificacion(int cod){
        verificiacion=cod;
    }

    public static TotemApp getInstanceClienteTotem(){
        if(totemApp == null)
            totemApp = new TotemApp();
        return totemApp;

    }

    public int getVerificiacion() {
        return verificiacion;
    }

    public void setVerificiacion(int verificiacion) {
        this.verificiacion = verificiacion;
    }
}
