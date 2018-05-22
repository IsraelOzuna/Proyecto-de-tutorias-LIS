package negocio;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Renato
 */
public class Asistencia {
    private int idAlumno;
    private String nombreAlumno;
    private CheckBox asistio;

    /**
     * @return the nombreAlumno
     */
    public Asistencia(int idAlumno, String nombreAlumno, CheckBox asistio){
        this.idAlumno=idAlumno;
        this.nombreAlumno=nombreAlumno;
        this.asistio=asistio;
        
    }

    Asistencia() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getNombreAlumno() {
        return nombreAlumno;
    }

    /**
     * @param nombreAlumno the nombreAlumno to set
     */
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    /**
     * @return the asistio
     */
    public CheckBox getAsistio() {
        return asistio;
    }

    /**
     * @param asistio the asistio to set
     */
    public void setAsistio(CheckBox asistio) {
        this.asistio = asistio;
    }

    /**
     * @return the idAlumno
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * @param idAlumno the idAlumno to set
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
    
}
