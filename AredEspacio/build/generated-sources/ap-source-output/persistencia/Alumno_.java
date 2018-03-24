package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-03-24T10:52:10")
@StaticMetamodel(Alumno.class)
public class Alumno_ { 

    public static volatile SingularAttribute<Alumno, String> apellidos;
    public static volatile SingularAttribute<Alumno, Integer> idAlumno;
    public static volatile SingularAttribute<Alumno, Date> fechaNacimiento;
    public static volatile SingularAttribute<Alumno, String> telefono;
    public static volatile SingularAttribute<Alumno, String> nombre;
    public static volatile SingularAttribute<Alumno, String> correoElectronico;
    public static volatile SingularAttribute<Alumno, String> rutaFoto;

}