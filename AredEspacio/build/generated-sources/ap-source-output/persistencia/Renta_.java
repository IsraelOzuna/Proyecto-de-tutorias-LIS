package persistencia;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2018-05-06T22:18:48")
@StaticMetamodel(Renta.class)
public class Renta_ { 

    public static volatile SingularAttribute<Renta, Date> horaFin;
    public static volatile SingularAttribute<Renta, Date> fecha;
    public static volatile SingularAttribute<Renta, String> nombreCliente;
    public static volatile SingularAttribute<Renta, Integer> idRenta;
    public static volatile SingularAttribute<Renta, Double> cantidad;
    public static volatile SingularAttribute<Renta, Date> horaInicio;

}