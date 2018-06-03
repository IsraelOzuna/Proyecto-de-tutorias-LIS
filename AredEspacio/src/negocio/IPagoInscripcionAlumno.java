/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Date;

/**
 *
 * @author Renato
 */
public interface IPagoInscripcionAlumno {    
    public boolean registrarInscripcion(double monto, int idAlumno, String nombreGrupo, Date fecha);
    
}
