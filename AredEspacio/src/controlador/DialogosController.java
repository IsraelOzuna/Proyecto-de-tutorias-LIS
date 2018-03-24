package controlador;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class DialogosController {
        /**
     * Muestra un mensaje de advertencia de acuerdo al fallo ocurrido
     * 
     * @param titulo nombre de la advertencia mostrada
     * @param encabezado un pequeño mensaje donde se muestra que ocurrió
     * @param contenido mayor retroalimentación      
     */
    public static void mostrarMensajeAdvertencia(String titulo, String encabezado, String contenido) {
        Alert advertencia = new Alert(Alert.AlertType.WARNING);
        advertencia.setTitle(titulo);
        advertencia.setHeaderText(encabezado);
        advertencia.setContentText(contenido);
        ButtonType botonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        advertencia.getButtonTypes().setAll(botonOK);
        advertencia.showAndWait();
    }
    
    /**
     * Muestra un mensaje de información acorde a lo ocurrido en el juego
     * 
     * @param titulo nombre de la advertencia mostrada
     * @param encabezado un pequeño mensaje donde se muestra que ocurrió
     * @param contenido mayor retroalimentación      
     */
    public static void mostrarMensajeInformacion(String titulo, String encabezado, String contenido) {
        Alert advertencia = new Alert(Alert.AlertType.INFORMATION);
        advertencia.setTitle(titulo);
        advertencia.setHeaderText(encabezado);
        advertencia.setContentText(contenido);
        ButtonType botonOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        advertencia.getButtonTypes().setAll(botonOK);
        advertencia.showAndWait();
    }
}
