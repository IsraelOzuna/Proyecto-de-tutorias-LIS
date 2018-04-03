package negocio;
import java.util.List;
import persistencia.Grupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Cuenta;
import persistencia.CuentaJpaController;
import persistencia.GrupoJpaController;
public class GrupoDAO implements IGrupo{

    @Override
    public List<Grupo> adquirirGrupos(persistencia.Cuenta usuario) {
        
        
        List<Grupo> listaGrupos=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);/////////Cambiar a AredEspacioPU
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        persistencia.Grupo grupo = new persistencia.Grupo();
        grupo.setUsuario(usuario);
        try{
            listaGrupos=grupoJpaController.findGrupoEntities();
        }catch(Exception ex){
            Logger.getLogger(GrupoDAO.class.getName());
        }
        return listaGrupos;
    }

    @Override
    public boolean crearGrupo(Grupo nuevoGrupo) {
        boolean grupoCreadoExitosamente=true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        persistencia.Grupo grupoNuevo = new persistencia.Grupo();
        grupoNuevo.setNombreGrupo(nuevoGrupo.getNombreGrupo());
        grupoNuevo.setUsuario(nuevoGrupo.getUsuario());
        grupoNuevo.setMensualidad(nuevoGrupo.getMensualidad());
        grupoNuevo.setInscripcion(nuevoGrupo.getInscripcion());
        try{
            grupoJpaController.create(grupoNuevo);
        }catch (Exception ex){
            grupoCreadoExitosamente=false;
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return grupoCreadoExitosamente;
    }

    @Override
    public List<Cuenta> adquirirCuentas() {
        List<Cuenta> listaCuentas=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);/////////Cambiar a AredEspacioPU
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);
        //persistencia.Cuenta cuenta = new persistencia.Cuenta();
        try{
            listaCuentas=cuentaJpaController.findCuentaEntities();
        }catch(Exception ex){
            Logger.getLogger(GrupoDAO.class.getName());
        }
        return listaCuentas;
    }

    @Override
    public Grupo adquirirGrupo(String nombreGrupo) {
        Grupo grupoEncontrado=new Grupo();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        try{
            grupoEncontrado=grupoJpaController.findGrupo(nombreGrupo);
        }catch (Exception ex){
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grupoEncontrado;
    }

    @Override
    public boolean eliminarGrupo(Grupo grupoEliminar) {
        boolean grupoEliminado=false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        try{
            grupoJpaController.destroy(grupoEliminar.getNombreGrupo());
            grupoEliminado=true;
        }catch (Exception ex){
            grupoEliminado=false;
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grupoEliminado;
    }
    
}
