package persistencia;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistencia.Grupo;
import persistencia.Maestro;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-03-23T16:40:10")
@StaticMetamodel(Cuenta.class)
public class Cuenta_ { 

    public static volatile CollectionAttribute<Cuenta, Grupo> grupoCollection;
    public static volatile SingularAttribute<Cuenta, String> tipoCuenta;
    public static volatile SingularAttribute<Cuenta, String> usuario;
    public static volatile SingularAttribute<Cuenta, String> contrasena;
    public static volatile SingularAttribute<Cuenta, Maestro> maestro;

}