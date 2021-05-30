package paquete.servidor;

import java.net.ServerSocket;

public interface I_Sincronizacion {
    void sincronizoServidores(String ip, int puerto);
    void leoServidor(ServerSocket serverSocket);
    void mandarUpdate(String ip, int puerto);
}
