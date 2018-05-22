package negocio;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IEgreso {
    public boolean registrarEgresoFb(Egreso recursoFb, String usuarioMaestro);
    public boolean registrarEgresoVariado(Egreso egresoVariado);
}
