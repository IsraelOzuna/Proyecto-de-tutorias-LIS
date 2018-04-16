package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-04-15T20:08:20")
@StaticMetamodel(Pagomaestro.class)
public class Pagomaestro_ { 

    public static volatile SingularAttribute<Pagomaestro, Date> fecha;
    public static volatile SingularAttribute<Pagomaestro, Integer> idPago;
    public static volatile SingularAttribute<Pagomaestro, String> usuario;
    public static volatile SingularAttribute<Pagomaestro, Double> cantidad;

}