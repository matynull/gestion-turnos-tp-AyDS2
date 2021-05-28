package paquete.totem.modelo;

import paquete.util.Paquete;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TotemApp {

    private static TotemApp totemApp;
    private int verificiacion=1;
    private boolean principal=true;

    private TotemApp() {
    }

    public void enviarPaquete(String dni){
        File archivo;
        if(principal)
            archivo = new File ("cfg.txt");
        else
            archivo = new File ( "cfg2.txt");
        try {
            String ip = null;
            String puerto = null;

            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            ip = br.readLine();
            puerto = br.readLine();
            fr.close();

            Socket socket = new Socket(ip, Integer.parseInt(puerto));
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(2000);
            os.writeObject(armaPaquete(dni));
            Paquete rta = (Paquete) is.readObject();

            Verificacion(rta.getCodigo());
            socket.close();
        }catch(SocketTimeoutException | ConnectException e){
            principal=!principal;
            enviarPaquete(dni);
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
