/**
 *Este controlador es usado para la retroalimentación al usuario a través
 * de diferentes mensajes
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
package controlador;

import java.util.Optional;
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

    /**
     * Muestra un mensaje para hacer saber al usuario que lo ingresado se
     * perderá
     *
     * @param titulo nombre de la advertencia mostrada
     * @param encabezado un pequeño mensaje
     * @param contenido mayor retroalimentación
     * @return una badera la cual indica si el usuario desea salir de la
     * pantalla
     */
    public static boolean mostrarMensajeCambios(String titulo, String encabezado, String contenido) {
        boolean salir;
        Alert cambios = new Alert(Alert.AlertType.CONFIRMATION);
        cambios.setTitle(titulo);
        cambios.setHeaderText(encabezado);
        cambios.setContentText(contenido);
        Optional<ButtonType> botonPresionado = cambios.showAndWait();
        if (botonPresionado.get() == ButtonType.OK) {
            salir = true;
        } else {
            salir = false;
        }
        return salir;
    }
}
