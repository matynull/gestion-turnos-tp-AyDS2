package paquete.totem.controlador;

import paquete.totem.modelo.TotemApp;
import paquete.totem.vista.VentanaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTotemApp implements ActionListener{
   private VentanaTotem vista;

    public ControladorTotemApp(VentanaTotem vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TotemApp.getInstanceClienteTotem().setDniActual(vista.getDNI());
        if(TotemApp.getInstanceClienteTotem().getVerificiacion()==0){
            vista.RegistroExitoso();
        }else{
            if(TotemApp.getInstanceClienteTotem().getVerificiacion()==2)
                vista.ServidoresCaidos();
            else
                vista.RegistroFallido();
        }
    }
}
