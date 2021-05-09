package paquete.controlador;

import paquete.modelo.Cliente;
import paquete.modelo.Empleado;
import paquete.modelo.EmpleadoApp;
import paquete.vista.VentanaEmpleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEmpleadoApp implements ActionListener {
    private VentanaEmpleado vista;
    private Cliente cliente;

    public ControladorEmpleadoApp(VentanaEmpleado vista) {
        this.vista = vista;
    }

    public void setEmpleadoApp(String nombre, int box){
        EmpleadoApp.getInstance().setEmpleado(new Empleado(nombre, box));
    }

    public void setCliente(Cliente cliente){
        this.cliente=cliente;
        vista.cliente.setText("Cliente: "+cliente.getDni());
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
            EmpleadoApp.getInstance().atiendeCliente();
        }
    }
}
