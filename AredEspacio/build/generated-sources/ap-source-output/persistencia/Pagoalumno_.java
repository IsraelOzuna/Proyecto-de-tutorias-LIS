package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistencia.Alumno;
import persistencia.Grupo;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-05-03T15:18:23")
@StaticMetamodel(Pagoalumno.class)
public class Pagoalumno_ { 

    public static volatile SingularAttribute<Pagoalumno, String> nombreGrupo;
    public static volatile SingularAttribute<Pagoalumno, Alumno> idAlumno;
    public static volatile SingularAttribute<Pagoalumno, Integer> idPago;
    public static volatile SingularAttribute<Pagoalumno, Grupo> grupo;
    public static volatile SingularAttribute<Pagoalumno, String> tipoPago;
    public static volatile SingularAttribute<Pagoalumno, Double> cantidad;
    public static volatile SingularAttribute<Pagoalumno, Date> fechaPago;
    public static volatile SingularAttribute<Pagoalumno, Grupo> idGrupo;

}