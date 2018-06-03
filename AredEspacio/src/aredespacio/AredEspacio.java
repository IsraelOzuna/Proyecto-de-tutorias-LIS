/*Sistema Ared espacio. Desarrollado para la escuela de danza
con el mismo nombre
Desarrollado por: Renato Vargas Gomez
                  Irvin Dereb Vera López
                  Israel Reyes Ozuna
*/


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
