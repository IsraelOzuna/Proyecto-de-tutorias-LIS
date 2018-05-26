package negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.CuentaJpaController;
import persistencia.MaestroJpaController;

/**
 *
 * @author Irvin Dereb Vera López
 * @author Israel Reyes Ozuna
 */
public class CuentaDAO implements ICuenta {

    @Override
    public boolean crearCuenta(Cuenta cuenta) {
        boolean cuentaRegistradaExitosamente = true;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        persistencia.Cuenta cuentaNueva = new persistencia.Cuenta();

        cuentaNueva.setContrasena(cuenta.getContraseña());
        cuentaNueva.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaNueva.setUsuario(cuenta.getUsuario());

        try {
            cuentaJpaController.create(cuentaNueva);

        } catch (Exception ex) {
            cuentaRegistradaExitosamente = false;
            Logger.getLogger(CuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentaRegistradaExitosamente;
    }

    @Override
    public boolean verificarNombreUsuarioRepetido(String nombreUsuario) {
        boolean usuarioRepetido = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        try {
            persistencia.Cuenta cuenta = cuentaJpaController.findCuenta(nombreUsuario);
            if (cuenta != null) {
                usuarioRepetido = true;
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarioRepetido;
    }

    @Override
    public String iniciarSesion(String nombreUsuario, String contrasena) {        
        String tipoInicioSesion = "";

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        try {
            persistencia.Cuenta cuenta = cuentaJpaController.findCuenta(nombreUsuario);
            if (cuenta != null) {
                if (cuenta.getTipoCuenta().equals("Director") && cuenta.getContrasena().equals(contrasena)) {
                    tipoInicioSesion = "Director";                    
                } else if (cuenta.getTipoCuenta().equals("Maestro") && cuenta.getContrasena().equals(contrasena)) {
                    tipoInicioSesion = "Maestro";                 
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipoInicioSesion;
    }

    @Override
    public persistencia.Cuenta obtenerCuentaMaestro(String nombreMaestro) {
        persistencia.Cuenta cuentaAdquirida = new persistencia.Cuenta();
        persistencia.Maestro maestroAdquirido = new persistencia.Maestro();
        List<persistencia.Maestro> listaMaestros;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);
        listaMaestros=maestroJpaController.findMaestroEntities();
        for(int i =0;i<listaMaestros.size();i++){
            if(listaMaestros.get(i).getNombre().equals(nombreMaestro)){
                maestroAdquirido=listaMaestros.get(i);
                break;
            }
        }
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);
        cuentaAdquirida=cuentaJpaController.findCuenta(maestroAdquirido.getUsuario());
        return cuentaAdquirida;
        
    }

    @Override
    public persistencia.Cuenta obtenerCuenta(String nombreUsuario) {
        persistencia.Cuenta cuentaAdquirida = new persistencia.Cuenta();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);
        cuentaAdquirida=cuentaJpaController.findCuenta(nombreUsuario);
        return cuentaAdquirida;
    }

}
