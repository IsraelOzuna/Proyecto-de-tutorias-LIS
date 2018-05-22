/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import persistencia.Grupo;

/**
 *
 * @author Renato
 */
public interface IAsistenciaDAO {
    public List<persistencia.Asistencia> obtenerAsistencia(Date fecha, int idGrupo);
    public boolean RegistrarAsistencia(int idAlumno, int idGrupo, Date fecha, String asistio);
}
