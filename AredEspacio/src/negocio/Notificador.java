package negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
/**
 * Esta clase contiene todos los metodos necesarios para la generación de 
 * notificaciones en el sistema
 * @author Renato Vargas Gómez
 * @version 1.0 / 5 de junio de 2018
 */
public class Notificador {
    
    public List<TextFlow> generarNotificacionesMaestro(persistencia.Cuenta usuario){
        List<TextFlow> notificaciones = new ArrayList();
        GrupoDAO grupoDAO = new GrupoDAO();
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        int idGrupo;
        persistencia.Alumno alumnoPago=new persistencia.Alumno();
        persistencia.Grupo grupoPago = new persistencia.Grupo();
        List<persistencia.Pagoalumnodireccion> listaPagosAlumnos = new ArrayList();
        PagoAlumnoDireccionDAO pagoAlumnoDAO = new PagoAlumnoDireccionDAO();
        listaPagosAlumnos=pagoAlumnoDAO.obtenerPagos(usuario);
        persistencia.Pagoalumnodireccion pagoActual;
        if(!listaPagosAlumnos.isEmpty()){
            for(int i=0; i<listaPagosAlumnos.size(); i++){
                pagoActual=listaPagosAlumnos.get(i);
                if(fechaTieneMenosDeUnaSemana(pagoActual.getFechaPago())){
                    grupoPago=pagoActual.getIdGrupo();
                    alumnoPago=pagoActual.getIdAlumno();
                    TextFlow flujoPago = new TextFlow();
                    Text encabezadoPago = new Text("Pago recibido por la direccion \n");
                    encabezadoPago.setStyle("-fx-font-weight: bold");
                    Text contenidoPago = new Text("La direccion recibió un pago del alumno \n"+
                            alumnoPago.getNombre()+" "+alumnoPago.getApellidos()+" para su grupo de: \n"+grupoPago.getNombreGrupo());
                    flujoPago.setStyle("-fx-border-color: black;");
                    flujoPago.getChildren().addAll(encabezadoPago, contenidoPago);
                    notificaciones.add(flujoPago);
                }
            }
        }
        return notificaciones;
    }
    
    public boolean fechaTieneMenosDeUnaSemana(Date fechaPago){
        Date fechaActual=new Date();
        boolean tieneMenosDeUnaSemana=false;
        
        Date fechaUnaSemana=new Date(); 
        Calendar c = Calendar.getInstance(); 
        c.setTime(fechaActual); 
        c.add(Calendar.DATE, -7);
        fechaUnaSemana = c.getTime();
        tieneMenosDeUnaSemana = fechaPago.after(fechaUnaSemana);
        return tieneMenosDeUnaSemana;
    }
    
    public  List<TextFlow> generarNotificacionesDirector(){
        Date fechaActual= new Date();
        List<TextFlow> notificaciones = new ArrayList();
        List<TextFlow> notificacionesRentas = new ArrayList();
        List<TextFlow> notificacionesPagos = new ArrayList();
        List<persistencia.Renta> listaRentas = adquirirRentas(fechaActual);
        if(!listaRentas.isEmpty()){
            for(int i=0; i<listaRentas.size(); i++){
                TextFlow flujoRenta = new TextFlow();
                Text encabezadoNotificacionRenta=new Text("Renta Proxima \n");
                encabezadoNotificacionRenta.setStyle("-fx-font-weight: bold");
                
                Text textoNotificacionRenta=new Text("Se aproxima una renta del cliente "+ 
                        listaRentas.get(i).getNombreCliente()+ "\n"+ "para el día "+
                        obtenerDiaEspanol(Utileria.mostrarFecha(listaRentas.get(i).getFecha()).getDayOfWeek().toString())+" "+
                        Utileria.mostrarFecha(listaRentas.get(i).getFecha()).getDayOfMonth()+
                        " de "+obtenerMes(Utileria.mostrarFecha(listaRentas.get(i).getFecha()).getMonthValue())+
                        " del " +Utileria.mostrarFecha(listaRentas.get(i).getFecha()).getYear());
                flujoRenta.setStyle("-fx-border-color: black;");
                flujoRenta.getChildren().addAll(encabezadoNotificacionRenta, textoNotificacionRenta);
                
                notificacionesRentas.add(flujoRenta);
            }
        }

        
        List<persistencia.Maestro> listaMaestros = adquirirMaestros(fechaActual);
        if(!listaMaestros.isEmpty()){
            for(int i=0; i<listaMaestros.size(); i++){
                TextFlow flujoMaestro = new TextFlow();
                Text encabezadoNotificacionPago=new Text("Se aproxima una fecha de corte \n");
                encabezadoNotificacionPago.setStyle("-fx-font-weight: bold");
                
                Text textoNotificacionPago=new Text("La fecha de corte para el Maestro: "+
                        listaMaestros.get(i).getNombre()+ listaMaestros.get(i).getApellidos()+"\n"+
                        "es el "+obtenerDiaEspanol(Utileria.mostrarFecha(listaMaestros.get(i).getFechaCorte()).getDayOfWeek().toString())+
                        " "+Utileria.mostrarFecha(listaMaestros.get(i).getFechaCorte()).getDayOfMonth()+ " de "
                        +obtenerMes(Utileria.mostrarFecha(listaMaestros.get(i).getFechaCorte()).getMonthValue()));
                
                flujoMaestro.setStyle("-fx-border-color: black;");
                flujoMaestro.getChildren().addAll(encabezadoNotificacionPago, textoNotificacionPago);
                notificacionesPagos.add(flujoMaestro);
            }
            
            
        
        }
        
        notificaciones.addAll(notificacionesRentas);
        notificaciones.addAll(notificacionesPagos);
        return notificaciones;
    }
    
    public List<persistencia.Renta> adquirirRentas(Date fechaHoy){
        List<persistencia.Renta> listaRentas = new ArrayList();
        
        Date fechaManana=new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(fechaHoy); 
        c.add(Calendar.DATE, 1);
        fechaManana = c.getTime();
        
        Date fechaPasadoManana=new Date(); 
        c.setTime(fechaManana); 
        c.add(Calendar.DATE, 1);
        fechaPasadoManana = c.getTime();
        
        Date fechaDosDias=new Date(); 
        c.setTime(fechaPasadoManana); 
        c.add(Calendar.DATE, 1);
        fechaDosDias = c.getTime();
        
        RentaDAO rentaDAO = new RentaDAO();
        List<persistencia.Renta> listaRentasManana = rentaDAO.obtenerRentasPorFecha(fechaManana);
        List<persistencia.Renta> listaRentasPasadoManana = rentaDAO.obtenerRentasPorFecha(fechaPasadoManana);
        List<persistencia.Renta> listaRentasDosDias = rentaDAO.obtenerRentasPorFecha(fechaDosDias);
        listaRentas.addAll(listaRentasManana);
        listaRentas.addAll(listaRentasPasadoManana);
        listaRentas.addAll(listaRentasDosDias);
        return listaRentas;
    }
    
    public List<persistencia.Maestro> adquirirMaestros(Date fechaHoy){
        List<persistencia.Maestro> listaMaestros = new ArrayList();
        
        Date fechaManana=new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(fechaHoy); 
        c.add(Calendar.DATE, 1);
        fechaManana = c.getTime();
        
        Date fechaPasadoManana=new Date(); 
        c.setTime(fechaManana); 
        c.add(Calendar.DATE, 1);
        fechaPasadoManana = c.getTime();
        
        Date fechaDosDias=new Date(); 
        c.setTime(fechaPasadoManana); 
        c.add(Calendar.DATE, 1);
        fechaDosDias = c.getTime();
        
        MaestroDAO maestroDAO = new MaestroDAO();
        List<persistencia.Maestro> listaMaestrosManana = maestroDAO.adquirirMaestrosPorFechaCorte(fechaManana);
        List<persistencia.Maestro> listaMaestrosPasadoManana = maestroDAO.adquirirMaestrosPorFechaCorte(fechaPasadoManana);
        List<persistencia.Maestro> listaMaestrosDosDias = maestroDAO.adquirirMaestrosPorFechaCorte(fechaDosDias);
        listaMaestros.addAll(listaMaestrosManana);
        listaMaestros.addAll(listaMaestrosPasadoManana);
        listaMaestros.addAll(listaMaestrosDosDias);
        return listaMaestros;
    }

    private String obtenerMes(int numeroMes){
        String mes="";
        switch(numeroMes){
            case 1:
                mes="Enero";
            break;
            case 2:
                mes="Febrero";
            break;
            case 3:
                mes="Marzo";
            break;
            case 4:
                mes="Abril";
            break;
            case 5:
                mes="Mayo";
            break;
            case 6:
                mes="Junio";
            break;
            case 7:
                mes="Julio";
            break;
            case 8:
                mes="Agosto";
            break;
            case 9:
                mes="Septiembre";
            break;
            case 10:
                mes="Octubre";
            break;
            case 11:
                mes="Noviembre";
            break;
            case 12:
                mes="Diciembre";
            break;
        }
        return mes;
    }
    
    public String obtenerDiaEspanol(String diaIngles){
        String diaEspanol="";
        switch(diaIngles){
            case "SUNDAY":
                diaEspanol="Domingo";
            break;
            case "MONDAY":
                diaEspanol="Lunes";
            break;
            case "TUESDAY":
                diaEspanol="Martes";
            break;
            case "WEDNESDAY":
                diaEspanol="Miercoles";
            break;
            case "THURSDAY":
                diaEspanol="Jueves";
            break;
            case "FRIDAY":
                diaEspanol="Viernes";
            break;
            case "SATURDAY":
                diaEspanol="Sábado";
            break;
        }
        return diaEspanol;
    
    }
}
