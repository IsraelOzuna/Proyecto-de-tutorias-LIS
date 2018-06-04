/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Date;

/**
 *
 * @author Renato Vargas Gómez
 */
public interface IPagoInscripcionAlumno {    
    /**
     * Este Metodo lleva a cabo el registro del pago de inscripcion
     * 
     * @param monto cantidad que se pagó
     * @param idAlumno id del alumno que se isncribirá
     * @param nombreGrupo nombre del grupo al que se inscribirá al alumno
     * @param fecha fecha en la que se inscribió al alumno
     * @return boolean representa si el registro se realizo correcatmente
     * @since 1.0 / 5 de junio de 2018
     **/
    public boolean registrarInscripcion(double monto, int idAlumno, String nombreGrupo, Date fecha);
    
}
