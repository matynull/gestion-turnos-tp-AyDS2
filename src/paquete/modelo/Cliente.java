package paquete.modelo;

public class Cliente {
    private String dni;

    public Cliente(){
    }

    public Cliente(String dni){
        this.dni=dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}