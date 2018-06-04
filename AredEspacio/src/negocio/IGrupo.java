package negocio;
import java.util.List;
import persistencia.Grupo;
/**
 *
 * @author Renato Vargas Gómez
 */
public interface IGrupo {
    /**
     * Este Metodo adquiere todos los grupos de un usuario en especifico
     * 
     * @param usuario usuario cuyos grupos serán adquirirdos
     * @return List conformada de los grupos adquiridos
     * @since 1.0 / 5 de junio de 2018
     **/
    public List<persistencia.Grupo> adquirirGrupos (persistencia.Cuenta usuario);
    
    /**
     * Este Metodo crea un nuevo grupo en el sistema
     * 
     * @param nuevoGrupo grupo nuevo que será registrado
     * @return boolean comprueba que el registro se haya realizado exitosamente
     * @since 1.0 / 5 de junio de 2018
     **/
    public boolean crearGrupo (Grupo nuevoGrupo);
   
    /**
     * Este Metodo adquiere todas las cuentas en el sistema
     * 
     * @return List con todas las cuentas adquiridas
     * @since 1.0 / 5 de junio de 2018
     **/
    public List<persistencia.Cuenta> adquirirCuentas ();
    
    /**
     * Este Metodo adquiere un grupo especifico con toda su iformación
     * 
     * @param idGrupo el identificado de un grupo cuyos dats serán adquiridos
     * @return Grupo consultado con todos sus datos
     * @since 1.0 / 5 de junio de 2018
     **/
    public Grupo adquirirGrupo (int idGrupo);
    
    /**
     * Este Metodo desactiva a un grupo para no estar disponible para inscribir 
     * alumnos o realizar asignación de horario
     * 
     * @param grupoEliminar
     * @return boolean que comprueba que la desactivación se realizó exitosamente
     * @since 1.0 / 5 de junio de 2018
     **/
    public boolean eliminarGrupo(Grupo grupoEliminar);
    
    /**
     * Este Metodo edita los datos de un grupo
     * 
     * @param grupoEditar el grupo que será editado con los datos modificados
     * @return boolean que valida que la edición se realizó correctamente
     * @since 1.0 / 5 de junio de 2018
     **/
    public boolean editarGrupo(Grupo grupoEditar);
    
    /**
     * Este Metodo adquiere todos los alumnos que pertenecen a un grupo
     * 
     * @param idGrupo identificador del grupo del que se obtendran lso alumnos
     * @return List con todos los alumnos pertenecientes al grupo
     * @since 1.0 / 5 de junio de 2018
     **/
    public List<persistencia.Alumno> obtenerAlumnos(int idGrupo);
}