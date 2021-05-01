package paquete.controlador;

import paquete.modelo.TotemApp;
import paquete.vista.VentanaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTotemApp implements ActionListener{
   private VentanaTotem vista;

    public ControladorTotemApp(VentanaTotem vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TotemApp.getInstanceClienteTotem().enviarPaquete(vista.getDNI());
        if(TotemApp.getInstanceClienteTotem().getVerificiacion()==0){
            vista.RegistroExitoso();
        }else{
            vista.RegistroFallido();
        }
    }
}
