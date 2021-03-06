package paquete.empleadoapp.modelo;

import paquete.empleadoapp.controlador.ControladorEmpleadoApp;
import paquete.util.Empleado;
import paquete.util.Paquete;
import paquete.util.Aplicacion;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class EmpleadoApp extends Aplicacion {

    private static EmpleadoApp empleadoApp;
    private Empleado empleado;
    private ControladorEmpleadoApp controlador;
    private boolean principal = true;
    private int intentos;

    private EmpleadoApp() {

    }

    public static EmpleadoApp getInstance() {
        if (empleadoApp == null)
            empleadoApp = new EmpleadoApp();
        return empleadoApp;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public void ejecutarApp() {
        Paquete paqueteRta;
        File archivo;
        if (principal)
            archivo = new File("cfg.txt");
        else
            archivo = new File("cfg2.txt");
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
            os.writeObject(armarPaquete());

            paqueteRta = (Paquete) is.readObject();
            if(paqueteRta.getCodigo()==404)
                throw new SocketTimeoutException();
            socket.close();
            intentos=0;
            administrarPaquete(paqueteRta);
        } catch (SocketTimeoutException | ConnectException e) {
            principal = !principal;
            intentos++;
            if(intentos>2)
                controlador.errorServidor();
            else
                this.ejecutarApp();
        } catch (IOException | ClassNotFoundException e) {
            controlador.errorServidor();
        }
    }

    @Override
    public Paquete armarPaquete(){
         return new Paquete(3, empleado.getBox(), controlador.getCliente());
    }

    @Override
    public void administrarPaquete(Paquete paqueteRta){
        if (paqueteRta.getCodigo() == 4) {
            controlador.noHayClientes();
        } else {
            controlador.setCliente(paqueteRta.getCliente());
        }
    }
    public void setControlador(ControladorEmpleadoApp c) {
        this.controlador = c;
    }
}
