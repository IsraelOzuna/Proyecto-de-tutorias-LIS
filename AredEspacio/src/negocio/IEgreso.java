package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IEgreso {
    public boolean registrarEgresoFb(Egreso recursoFb, String usuarioMaestro);
    public boolean registrarEgresoVariado(Egreso egresoVariado);
    public List<persistencia.Egreso> obtenerTodosLosEgresos(int allo, int mes);
}
