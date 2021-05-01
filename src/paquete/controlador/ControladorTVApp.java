package paquete.controlador;

import paquete.modelo.TVApp;
import paquete.vista.VentanaTV;

public class ControladorTVApp {

    private VentanaTV vista;

    public ControladorTVApp(VentanaTV vista) {
        this.vista = vista;
        new Thread(()->{
            while (true){
                TVApp.getInstance().refrescarTV();
                this.vista.RefreshTable(TVApp.getInstance().getClientesSiendoAtendidos(),TVApp.getInstance().getClientes());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
