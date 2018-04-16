/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;

/**
 *
 * @author Irdevelo
 */
public interface IMaestro {
    
    public boolean registrarMaestro(Maestro maestro);
    
     public boolean editarMaestro(persistencia.Maestro maestro);
    
    public List<persistencia.Maestro> buscarMaestro(String nombre); 

    public int obtenerNumeroMaestros();
    
    public List<persistencia.Maestro> adquirirMaestros();
    
    public List<persistencia.Alumno> obtenerAlumnos(String nombreGrupo);
    
    
}
