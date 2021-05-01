package paquete.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TotemApp {

    private static TotemApp totemApp;
    private int verificiacion=1;

    private TotemApp() {
    }

    public void enviarPaquete(String dni){
        try {
            Socket socket = new Socket("localhost",9000);
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
