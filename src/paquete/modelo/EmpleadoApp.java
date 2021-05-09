package paquete.modelo;

import paquete.controlador.ControladorEmpleadoApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EmpleadoApp {

    private static EmpleadoApp empleadoApp;
    private Empleado empleado;
    private ControladorEmpleadoApp controlador;
    private int verificacion=1;

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
        try {
            Paquete paqueteRta;
            Socket socket = new Socket("localhost", 9000);
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

    public int getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(int verificacion) {
        this.verificacion = verificacion;
    }
}
