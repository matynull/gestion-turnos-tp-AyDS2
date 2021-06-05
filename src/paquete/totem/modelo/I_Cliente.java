package paquete.totem.modelo;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface I_Cliente {
    public void enviarPaquete(ObjectOutputStream os) throws IOException;
}
