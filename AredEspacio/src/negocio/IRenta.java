/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Irdevelo
 */
public interface IRenta {

    public List<persistencia.Renta> obtenerRentas();

    public List<persistencia.Renta> obtenerRentasPorFecha(Date fecha);

    public boolean registrarRenta(Renta renta);

    public boolean eliminarRenta(int id);
    
    public boolean editarRenta(Renta renta, int idRenta);

}
