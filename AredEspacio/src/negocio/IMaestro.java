package negocio;

import java.util.Date;
import java.util.List;

public interface IMaestro {
    
    public boolean registrarMaestro(Maestro maestro);
    
    public boolean editarMaestro(persistencia.Maestro maestro);
    
    public List<persistencia.Maestro> buscarMaestro(String nombre); 

    public int obtenerNumeroMaestros();
    
    public List<persistencia.Maestro> adquirirMaestros();
    
    public String adquirirNombreMaestroPorNombreDeUsuario(String nombreUsuario);
    
    public persistencia.Maestro adquirirMaestro(String nombreMaestro);
    
    public List<persistencia.Maestro> adquirirMaestrosPorFechaCorte(Date fechaCorte);
}
