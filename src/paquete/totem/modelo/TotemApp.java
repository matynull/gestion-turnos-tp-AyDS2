package paquete.totem.modelo;

import paquete.util.Paquete;
import paquete.util.Aplicacion;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TotemApp extends Aplicacion implements I_Cliente {

    private static TotemApp totemApp;
    private int verificiacion=1,intentos=0;
    private boolean principal=true;
    private String dniActual;

    private TotemApp() {
    }

    public void ejecutarApp(){
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
            enviarPaquete(os);
            Paquete rta = (Paquete) is.readObject();
            if(rta.getCodigo()==404)
                throw new SocketTimeoutException();
            administrarPaquete(rta);
            intentos=0;
            socket.close();
        }catch(SocketTimeoutException | ConnectException e){
            principal=!principal;
            intentos++;
            if(intentos>2)
                setVerificiacion(2);
            else
                ejecutarApp();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paquete armarPaquete(){
        return new Paquete(dniActual);
    }

    @Override
    public void administrarPaquete(Paquete rta){
        Verificacion(rta.getCodigo());
    }

    @Override
    public void enviarPaquete(ObjectOutputStream os) throws IOException{
        os.writeObject(armarPaquete());
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

    public String getDniActual() {
        return dniActual;
    }

    public void setDniActual(String dniActual) {
        this.dniActual = dniActual;
        ejecutarApp();
    }
}
