/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;
import persistencia.Pagomaestro;

/**
 *
 * @author Irdevelo
 */
public interface IPagoMaestro {
    
    public boolean registrarPagoMaestro(PagoMaestro pagoMaestro);
    public List<Pagomaestro> obtenerPagosMaestros(int allo, int mes);
    
}
