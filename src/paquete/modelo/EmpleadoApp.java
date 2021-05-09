package paquete.modelo;

import paquete.controlador.ControladorEmpleadoApp;
import paquete.util.Empleado;
import paquete.util.Paquete;

import java.io.*;
import java.net.Socket;

public class EmpleadoApp {

    private static EmpleadoApp empleadoApp;
    private Empleado empleado;
    private ControladorEmpleadoApp controlador;

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

    public void atiendeCliente() {
        Paquete paqueteRta;
        File archivo = new File ("cfg.txt");
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
            os.writeObject(new Paquete(3,empleado.getBox(),controlador.getCliente()));
            paqueteRta = (Paquete) is.readObject();
            if (paqueteRta.getCodigo() == 4) {
                controlador.noHayClientes();
            } else {
                controlador.setCliente(paqueteRta.getCliente());
            }
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            controlador.errorServidor();
        }
    }
    public void setControlador(ControladorEmpleadoApp c){
        this.controlador= c;
    }
}
