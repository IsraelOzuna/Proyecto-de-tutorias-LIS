package negocio;
import java.util.ArrayList;
import java.util.List;
import persistencia.Grupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Cuenta;
import persistencia.CuentaJpaController;
import persistencia.GrupoJpaController;
import persistencia.MaestroJpaController;
public class GrupoDAO implements IGrupo{
    String unidadPersistencia;
    public GrupoDAO(String unidadPersistencia){
        this.unidadPersistencia=unidadPersistencia;
    }
    
    @Override
    public List<Grupo> adquirirGrupos(persistencia.Cuenta usuario) {
        List<Grupo> listaGrupos=null;
        List<Grupo> listaGrupoAuxiliar= new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        persistencia.Grupo grupo = new persistencia.Grupo();
        grupo.setUsuario(usuario);
        try{
            listaGrupos=grupoJpaController.findGrupoEntities();
            if(usuario.getTipoCuenta().equals("Maestro")){
                for(int i=0; i<listaGrupos.size();i++){
                    if(listaGrupos.get(i).getUsuario().getUsuario().equals(usuario.getUsuario())){
                        listaGrupoAuxiliar.add(listaGrupos.get(i));
                    }
                }
                listaGrupos=listaGrupoAuxiliar;
            }
        }catch(Exception ex){
            Logger.getLogger(GrupoDAO.class.getName());
        }
        return listaGrupos;
    }

    @Override
    public boolean crearGrupo(Grupo nuevoGrupo) {
        boolean grupoCreadoExitosamente=true;
        if(nuevoGrupo.getNombreGrupo()==null){
            grupoCreadoExitosamente=false;
        }else{
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);//Cambiar a "AredEspacioPU"
            GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
            
            try{
                grupoJpaController.create(nuevoGrupo);
            }catch (Exception ex){
                grupoCreadoExitosamente=false;
                Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        return grupoCreadoExitosamente;
    }

    @Override
    public List<Cuenta> adquirirCuentas() {
        List<Cuenta> listaCuentas=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);/////////Cambiar a AredEspacioPU
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
    public Grupo adquirirGrupo(int idGrupo) {
        Grupo grupoEncontrado=new Grupo();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        try{
            grupoEncontrado=grupoJpaController.findGrupo(idGrupo);
        }catch (Exception ex){
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grupoEncontrado;
    }

    @Override
    public boolean eliminarGrupo(Grupo grupoEliminar) {
        boolean grupoEliminado=false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        try{
            grupoJpaController.edit(grupoEliminar);
            grupoEliminado=true;
            //grupoJpaController.destroy(grupoEliminar.getNombreGrupo());
            
        }catch (Exception ex){
            grupoEliminado=false;
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grupoEliminado;
    }

    @Override
    public boolean editarGrupo(Grupo grupoEditar) {
        boolean grupoEditado=true;
        //grupoEditar.setEstaActivo(1);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        
        try{
            grupoJpaController.edit(grupoEditar);
            grupoEditado=true;
        }catch (Exception ex){
            grupoEditado=false;
        }
        
        return grupoEditado;
    }
    @Override
    public List<persistencia.Alumno> obtenerAlumnos(int idGrupo) {
        List<persistencia.Alumno> listaAlumnos=null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        listaAlumnos=grupoJpaController.obtenerListaAlumnos(idGrupo);
        return listaAlumnos;
    }
}
