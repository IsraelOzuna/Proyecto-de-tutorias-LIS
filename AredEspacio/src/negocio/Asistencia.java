package negocio;

import javafx.scene.control.CheckBox;

/**
 * Esta clase contiene todos los atributos necesarios para manipular una 
 * asistencia a través de una interfaz gráfica
 *
 * @author Renato Vargas Gómez
 * @version 1.0 / 5 de junio de 2018
 */
public class Asistencia {
    private int idAlumno;
    private String nombreAlumno;
    private CheckBox asistio;


    public Asistencia(int idAlumno, String nombreAlumno, CheckBox asistio){
        this.idAlumno=idAlumno;
        this.nombreAlumno=nombreAlumno;
        this.asistio=asistio;
        
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public CheckBox getAsistio() {
        return asistio;
    }

    public void setAsistio(CheckBox asistio) {
        this.asistio = asistio;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
    
}
