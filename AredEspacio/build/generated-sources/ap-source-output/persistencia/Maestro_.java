package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistencia.Cuenta;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-03-23T16:40:10")
@StaticMetamodel(Maestro.class)
public class Maestro_ { 

    public static volatile SingularAttribute<Maestro, String> apellidos;
    public static volatile SingularAttribute<Maestro, Date> fechaCorte;
    public static volatile SingularAttribute<Maestro, Cuenta> cuenta;
    public static volatile SingularAttribute<Maestro, String> usuario;
    public static volatile SingularAttribute<Maestro, Integer> estaActivo;
    public static volatile SingularAttribute<Maestro, Double> mensualidad;
    public static volatile SingularAttribute<Maestro, String> telefono;
    public static volatile SingularAttribute<Maestro, String> nombre;
    public static volatile SingularAttribute<Maestro, String> correoElectronico;
    public static volatile SingularAttribute<Maestro, String> rutaFoto;

}