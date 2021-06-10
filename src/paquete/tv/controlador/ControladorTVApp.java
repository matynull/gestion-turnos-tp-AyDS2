package paquete.tv.controlador;

import paquete.tv.modelo.TVApp;
import paquete.tv.vista.VentanaTV;

public class ControladorTVApp {

    private VentanaTV vista;

    public ControladorTVApp(VentanaTV vista) {
        this.vista = vista;
        new Thread(()->{
            while (true){
                TVApp.getInstance().ejecutarApp();
                this.vista.RefreshTable(TVApp.getInstance().getClientesSiendoAtendidos(),TVApp.getInstance().getClientes().getList());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
