package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.Pagoalumno;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-05-03T13:50:53")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile CollectionAttribute<Grupo, Alumno> alumnoCollection;
    public static volatile SingularAttribute<Grupo, String> nombreGrupo;
    public static volatile SingularAttribute<Grupo, Double> inscripcion;
    public static volatile CollectionAttribute<Grupo, Pagoalumno> pagoalumnoCollection;
    public static volatile SingularAttribute<Grupo, Cuenta> usuario;
    public static volatile SingularAttribute<Grupo, Integer> estaActivo;
    public static volatile SingularAttribute<Grupo, Double> mensualidad;
    public static volatile SingularAttribute<Grupo, Date> fechaPago;
    public static volatile SingularAttribute<Grupo, Integer> idGrupo;

}