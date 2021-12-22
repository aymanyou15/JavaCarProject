/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkboxandscreenfeature;

import javafx.geometry.Insets;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author modat
 */
public class CheckBoxandScreenFeature extends Application {

    boolean direction = true;

    @Override
    public void start(Stage primaryStage) {
        Text txt1 = new Text("ClockWise");        
        txt1.setId("text");      //style the text from the CSS file

        Reflection r = new Reflection();
        r.setFraction(0.7f);    //reflection degree
        txt1.setEffect(r);       //reflect text

        CheckBox box = new CheckBox("ClockWise");   //adding checkbox
        box.setSelected(true);  //check checkbox

        box.setOnAction(event -> {      //event handlling the checkbox
            if (box.isSelected()) {
                txt1.setText("ClockWise");
                /*
                send data via bluetooth
                */
            } else {
                txt1.setText("Anti-ClockWise");
                /*
                send data via bluetooth
                */
            }
        });

        Rectangle screen = new Rectangle();
        screen = new Rectangle(0, 100, 450, 200);    //set the screen
        screen.setId("screen");     //style the screen from the CSS file

        StackPane root = new StackPane();   //stack panels
        root.getChildren().add(screen);         //screen        
        root.getChildren().add(txt1);        //text
        
        //layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(box, root);

        Scene scene = new Scene(layout, 500, 500);    //set the window
        
        //CSS file
        File f = new File("C:\\Users\\modat\\Documents\\NetBeansProjects\\CheckBoxandScreenFeature\\src\\css\\newCascadeStyleSheet.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        primaryStage.setTitle("CheckBox With Screen Feature");        //set the title of the window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);   //launch
    }

}
