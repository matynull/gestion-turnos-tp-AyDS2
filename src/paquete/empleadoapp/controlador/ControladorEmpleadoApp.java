package paquete.empleadoapp.controlador;

import paquete.util.Cliente;
import paquete.util.Empleado;
import paquete.empleadoapp.modelo.EmpleadoApp;
import paquete.empleadoapp.vista.VentanaEmpleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEmpleadoApp implements ActionListener, I_TurnoSiguiente {
    private VentanaEmpleado vista;
    private Cliente cliente;

    public ControladorEmpleadoApp(VentanaEmpleado vista) {
        this.vista = vista;
    }

    public void setEmpleadoApp(String nombre, int box){
        EmpleadoApp.getInstance().setEmpleado(new Empleado(nombre, box));
    }

    @Override
    public void setCliente(Cliente cliente){
        this.cliente=cliente;
        vista.cliente.setText("Cliente: "+cliente.getNombre());
    }

    public void noHayClientes(){
        vista.errorClientes();
    }

    public void errorServidor(){
        vista.errorServidor();
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("TURNO")){
            EmpleadoApp.getInstance().ejecutarApp();
        }
    }
}
