package paquete.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EmpleadoApp {

    private static EmpleadoApp empleadoApp;
    private Empleado empleado;

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
            os.writeObject(new Paquete(3));
            paqueteRta = (Paquete) is.readObject();
            if (paqueteRta.getCodigo() == 4) {
                // manejar esto
            } else {
                //llega cliente
            }
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
