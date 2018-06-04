package negocio;

/**
 * Esta clase contiene todos los atributos necesarios para manipular un 
 * ingreso a través de una interfaz gráfica
 *
 * @author Renato Vargas Gómez
 * @version 1.0 / 5 de junio de 2018
 */
public class Ingreso {
    private String razon;
    private double cantidad;
    private int mes;
    private int allo;

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAllo() {
        return allo;
    }

    public void setAllo(int allo) {
        this.allo = allo;
    }
    
}
