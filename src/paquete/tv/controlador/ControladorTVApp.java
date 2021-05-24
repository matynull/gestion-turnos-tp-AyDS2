package paquete.tv.controlador;

import paquete.tv.modelo.TVApp;
import paquete.tv.vista.VentanaTV;

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
