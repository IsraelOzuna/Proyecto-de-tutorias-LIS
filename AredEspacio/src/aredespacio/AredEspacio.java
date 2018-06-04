/*--------------------------------------------------------*/
/*Programa: Ared Espacio
  Desarrolladores: Irvin Dereb Vera López
                   Renato Vargas Gómez
                   Israel Reyes Ozuna
  Fecha: 5 de junio de 2018
  Descripción: El programa a continuación presentado permite
               Llevar el control de la escuela de danza 
               "Ared espacio" con el fin de automatizar
               tareas que ayuden el manejo de la escuela  */
/*--------------------------------------------------------*/      

package aredespacio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AredEspacio extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/VentanaInicioSesion.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);      
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
