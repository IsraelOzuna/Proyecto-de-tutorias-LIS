/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.AsistenciaJpaController;
import persistencia.Grupo;

/**
 *
 * @author Renato
 */
public class AsistenciaDAO implements IAsistencia{
    private String unidadPersistencia="AredEspacioPU";
    
    public AsistenciaDAO(){
    }
    
    public AsistenciaDAO(String unidadPersistencia){
        this.unidadPersistencia=unidadPersistencia;
    }
    
    @Override
    public List<persistencia.Asistencia> obtenerAsistencia(Date fecha, int idGrupo) {
        boolean asistio=false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        AsistenciaJpaController asistenciaJpaController = new AsistenciaJpaController(entityManagerFactory);
        List<persistencia.Asistencia> listaAsistencias;
        
        listaAsistencias=asistenciaJpaController.obtenerAsistenciasPorFecha(fecha);
        listaAsistencias=obtenerListaPorGrupo(listaAsistencias, idGrupo);
        
        
        return listaAsistencias;
    }
    
    public List<persistencia.Asistencia> obtenerListaPorGrupo(List<persistencia.Asistencia> listaPorFecha, int idGrupo){
        List<persistencia.Asistencia> listaPorGrupo= new ArrayList();
        for(int i=0; i<listaPorFecha.size(); i++){
            if(listaPorFecha.get(i).getIdGrupo().getIdGrupo() == idGrupo){
                listaPorGrupo.add(listaPorFecha.get(i));
            }
        }
        return listaPorGrupo;
    }
    public List<persistencia.Asistencia> obtenerListaPorAlumno(List<persistencia.Asistencia> listaPorGrupo, int idAlumno){
        List<persistencia.Asistencia> listaPorAlumno= new ArrayList();
        for(int i=0; i<listaPorGrupo.size(); i++){
            if(listaPorGrupo.get(i).getIdAlumno().getIdAlumno() == idAlumno){
                listaPorGrupo.add(listaPorGrupo.get(i));
            }
        }
        return listaPorAlumno;
    }

    @Override
    public boolean RegistrarAsistencia(int idAlumno, int idGrupo, Date fecha, String asistio) {
        boolean registrada=false;
        boolean asistenciaRepetida=false;
        boolean alumnoEncontrado=false;
        int contador=0;
        int idAsistencia=0;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        Grupo grupo = grupoDAO.adquirirGrupo(idGrupo);
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        persistencia.Alumno alumno = alumnoDAO.adquirirAlumno(idAlumno);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        AsistenciaJpaController asistenciaJpaController = new AsistenciaJpaController(entityManagerFactory);
        persistencia.Asistencia asistencia= new persistencia.Asistencia();
        asistencia.setIdAlumno(alumno);
        asistencia.setIdGrupo(grupo);
        asistencia.setFecha(fecha);
        asistencia.setAsistio(asistio);
        ///////////////////////////////////////Validar que la asistencia no se repita
        List<persistencia.Asistencia> listaAsistencias = new ArrayList();
        listaAsistencias=obtenerAsistencia(fecha, idGrupo);
        System.out.println(listaAsistencias.size());
        
        if(listaAsistencias.size()>0){
            for(int i =0; i<listaAsistencias.size();i++){
                if(listaAsistencias.get(i).getIdAlumno().getIdAlumno()==idAlumno){
                    alumnoEncontrado=true;
                    asistenciaRepetida=true;
                    idAsistencia=listaAsistencias.get(i).getIdAsistencia();
                    break;
                }else{
                    alumnoEncontrado=false;
                    asistenciaRepetida=false;
                }
            }
            
        }else{
            asistenciaRepetida=false;
        }
        
        
        
        if(asistenciaRepetida){
            try {
                asistencia.setIdAsistencia(idAsistencia);
                asistenciaJpaController.edit(asistencia);
                registrada=true;
                } catch (Exception ex) {
                    registrada=false;
                }

        }else{
            try{
                asistenciaJpaController.create(asistencia);
                registrada=true;
            }catch (Exception ex){
                registrada=false;
            }
            
        }
        return registrada;
    }
}
