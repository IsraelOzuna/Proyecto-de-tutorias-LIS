/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.MaestroJpaController;

/**
 *
 * @author Irdevelo
 */
public class MaestroDAO implements IMaestro {

    @Override
    public boolean registrarMaestro(Maestro maestro) {
        boolean maestroRegistradoExitosamente = true;

        if (maestro != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);

            persistencia.Maestro maestroNuevo = new persistencia.Maestro();

            maestroNuevo.setNombre(maestro.getNombre());
            maestroNuevo.setApellidos(maestro.getApellidos());
            maestroNuevo.setCorreoElectronico(maestro.getCorreoElectronico());
            maestroNuevo.setTelefono(maestro.getTelefono());
            maestroNuevo.setEstaActivo(maestro.getEstaActivo());
            maestroNuevo.setFechaCorte(maestro.getFechaCorte());
            maestroNuevo.setRutaFoto(maestro.getRutaFoto());
            maestroNuevo.setMensualidad(maestro.getMensualidad());
            maestroNuevo.setUsuario(maestro.getUsuario());

            try {
                maestroJpaController.create(maestroNuevo);

            } catch (Exception ex1) {
                maestroRegistradoExitosamente = false;
                Logger.getLogger(MaestroDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } else {
            maestroRegistradoExitosamente = false;
        }
        return true;
    }

    @Override
    public List<persistencia.Maestro> buscarMaestro(String nombre) {
        List<persistencia.Maestro> maestrosEncontrados = null;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);

        persistencia.Maestro maestro = new persistencia.Maestro();

        maestro.setNombre(nombre);

        try {
            maestrosEncontrados = maestroJpaController.obtenerMaestros(nombre);

        } catch (Exception ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return maestrosEncontrados;
    }

    @Override
    public int obtenerNumeroMaestros() {
        int numeroMaestros=0;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);
        numeroMaestros = maestroJpaController.getMaestroCount();
        return numeroMaestros;
    }

    @Override
    public List<persistencia.Maestro> adquirirMaestros() {
        List<persistencia.Maestro> listaMaestros=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);
        listaMaestros=maestroJpaController.findMaestroEntities();
        return listaMaestros;
    }

    @Override
    public List<Alumno> obtenerAlumnos(String nombreGrupo) {
        List<persistencia.Alumno> listaAlumnos=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);
        listaAlumnos=maestroJpaController.obtenerListaAlumnos(nombreGrupo);
        return listaAlumnos;
    }

}
