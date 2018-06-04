package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Egreso;
import negocio.EgresoDAO;
import negocio.Ingreso;
import negocio.MaestroDAO;
import negocio.PagoMaestroDAO;
import negocio.RentaDAO;
import negocio.Utileria;
import persistencia.Maestro;
import persistencia.Pagomaestro;
import persistencia.Renta;

/**
 * Este controlador es usado para presentar un reporte simple de los ingresos y
 * egresos registrados
 *
 * @author Renato Vargas
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaGenerarReporteController implements Initializable {

    @FXML
    private TableView<Ingreso> tablaGanancias;
    @FXML
    private TableColumn columnaRazonGanancia;
    @FXML
    private TableColumn columnaCantidadGanacias;
    @FXML
    private TableView<negocio.Egreso> tablaPerdidas;
    @FXML
    private TableColumn columnaRazonPerdidas;
    @FXML
    private TableColumn columnaCantidadPerdidas;
    @FXML
    private Label etiquetaGananciaTotal;
    @FXML
    private Label etiquetaPerdidaTotal;
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private Label etiquetaMes;
    @FXML
    private Label etiquetaAllo;
    @FXML
    private Button botonAnterior;
    private int mesActual;
    private int alloActual;
    ObservableList<Ingreso> ganancias;
    ObservableList<negocio.Egreso> perdidas;

    /**
     * Este método establece el tipo de datos que pueden tener las tablas
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarTablaGanancias(){
        columnaRazonGanancia.setCellValueFactory(new PropertyValueFactory<Ingreso, String>("razon"));
        columnaCantidadGanacias.setCellValueFactory(new PropertyValueFactory<Ingreso, String>("cantidad"));
        ganancias = FXCollections.observableArrayList();
        tablaGanancias.setItems(ganancias);
        
        columnaRazonPerdidas.setCellValueFactory(new PropertyValueFactory<negocio.Egreso, String>("nombreGasto"));
        columnaCantidadPerdidas.setCellValueFactory(new PropertyValueFactory<negocio.Egreso, String>("costo"));
        perdidas = FXCollections.observableArrayList();
        tablaPerdidas.setItems(perdidas);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    /**
     * Este método establece tdos los datos dentro de la ventana desde que 
     * se despliega por primera vez
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarVentana(){
        Date fecha = new Date();
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int mes = localDate.getMonthValue();
        int allo = localDate.getYear();
        mesActual=mes;
        alloActual=allo;
        etiquetaAllo.setText(String.valueOf(alloActual));
        etiquetaMes.setText(obtenerMes(mes));
        iniciarTablaGanancias();
        actualizarTablas();
    }

    @FXML
    private void desplegarVentanaEgreso(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEgresoVariado.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEgresoVariadoController ventanaEgresoVariado = loader.getController();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void regresarMes(ActionEvent event) {
        ganancias.clear();
        perdidas.clear();
        if(mesActual==1){
            mesActual=12;
            alloActual--;
            etiquetaAllo.setText(String.valueOf(alloActual));
        }else{
            mesActual--;
        }
        etiquetaMes.setText(obtenerMes(mesActual));
        
        actualizarTablas();
    }

    @FXML
    private void siguienteMes(ActionEvent event) {
        ganancias.clear();
        perdidas.clear();
        if(mesActual==12){
            mesActual=1;
            alloActual++;
            etiquetaAllo.setText(String.valueOf(alloActual));
        }else{
            mesActual++;
        }
        etiquetaMes.setText(obtenerMes(mesActual));
        
        actualizarTablas();
    }
    

    private void actualizarTablas(){
        
        PagoMaestroDAO pagoDAO = new PagoMaestroDAO();
        RentaDAO rentaDAO = new RentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        EgresoDAO egresoDAO = new EgresoDAO();
        List<persistencia.Egreso> listaEgresos= egresoDAO.obtenerTodosLosEgresos(alloActual, mesActual);
        
        List<Pagomaestro> listaPagos=pagoDAO.obtenerPagosMaestros(alloActual, mesActual);
        List<Renta> listaRentas = rentaDAO.obtenerRentasPorMesAllo(mesActual, alloActual);
        LocalDate localDate;
        
        for(int i =0; i<listaPagos.size(); i++){
            localDate=Utileria.mostrarFecha(listaPagos.get(i).getFecha());
            Maestro maestroPago =new Maestro();
            maestroPago.setNombre(maestroDAO.adquirirNombreMaestroPorNombreDeUsuario(listaPagos.get(i).getUsuario()));
            Ingreso ingreso=new Ingreso();
            ingreso.setRazon("Pago recibido de: "+maestroPago.getNombre());
            ingreso.setCantidad(listaPagos.get(i).getCantidad());
            ingreso.setAllo(localDate.getYear());
            ingreso.setMes(localDate.getMonthValue());
            ganancias.add(ingreso);
        }
        
        for(int i =0; i<listaRentas.size(); i++){
            localDate=Utileria.mostrarFecha(listaRentas.get(i).getFecha());
            Ingreso ingreso=new Ingreso();
            ingreso.setRazon("Renta de : "+listaRentas.get(i).getNombreCliente());
            ingreso.setCantidad(listaRentas.get(i).getCantidad());
            ingreso.setAllo(localDate.getYear());
            //System.out.println(ingreso.getAllo());
            ingreso.setMes(localDate.getMonthValue());
            //System.out.println(ingreso.getMes());
            ganancias.add(ingreso);
            
        }
        
        for(int i =0; i<listaEgresos.size(); i++){
            localDate=Utileria.mostrarFecha(listaEgresos.get(i).getFechaRegistro());
            Egreso egreso=new Egreso();
            egreso.setNombreGasto(listaEgresos.get(i).getNombreGasto());
            egreso.setCosto(listaEgresos.get(i).getCosto());
            egreso.setFechaRegistro(listaEgresos.get(i).getFechaRegistro());
            perdidas.add(egreso);
        }

        for(int i=0; i<ganancias.size(); i++){
            if(ganancias.get(i).getMes()!=mesActual){
                ganancias.remove(i);
            }else if(ganancias.get(i).getAllo()!=alloActual){
                ganancias.remove(i);
            }
        }
        
        etiquetaGananciaTotal.setText(calcularGanancia());
        etiquetaPerdidaTotal.setText(calcularPerdida());
    }
    
    private String calcularGanancia(){
        String resultado="0";
        double resultadoEntero=0;
        for(int i=0; i<ganancias.size(); i++){
            resultadoEntero=resultadoEntero+ganancias.get(i).getCantidad();
        }
        resultado=String.valueOf(resultadoEntero);
        return resultado;
    }
    
    private String calcularPerdida(){
        String resultado="0";
        double resultadoEntero=0;
        for(int i=0; i<perdidas.size(); i++){
            resultadoEntero=resultadoEntero+perdidas.get(i).getCosto();
        }
        resultado=String.valueOf(resultadoEntero);
        return resultado;
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
}
