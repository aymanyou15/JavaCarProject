/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package car_motor;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Car extends Application{

     @Override
     public void start(Stage primaryStage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
       primaryStage.initStyle(StageStyle.UNDECORATED);
       Scene scene=new Scene(root);
       
       primaryStage.setScene(scene);
       primaryStage.show();
       
     }
    
    public static void main(String args[]) {
        // TODO code application logic here
        launch(args);
    }
}
