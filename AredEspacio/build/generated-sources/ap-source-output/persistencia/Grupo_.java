package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistencia.Cuenta;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-03-23T16:40:10")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, String> nombreGrupo;
    public static volatile SingularAttribute<Grupo, Cuenta> usuario;
    public static volatile SingularAttribute<Grupo, Double> mensualidad;
    public static volatile SingularAttribute<Grupo, Date> fechaPago;

}