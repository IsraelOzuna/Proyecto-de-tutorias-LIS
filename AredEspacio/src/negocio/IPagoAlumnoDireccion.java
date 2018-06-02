/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;

/**
 *
 * @author Renato
 */
public interface IPagoAlumnoDireccion {
    boolean registrarPagoDireccion(persistencia.Pagoalumnodireccion pago);
    List<persistencia.Pagoalumnodireccion> obtenerPagos(persistencia.Cuenta usuario);
}
