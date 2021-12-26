/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javamotordriver;

import eu.hansolo.medusa.Gauge;
import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ayman elgammal
 */
public class FXMLDocumentController implements Initializable {

    public AnchorPane anchor;

    public Button speed;
    
    public Slider verticalSlider;
    
    public Gauge spedometer;
    
    int x=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    public void anchorOnKeyPressed(KeyEvent keyEvent){
        
    }
    

    public void btnOnMouseClick(MouseEvent mouseEvent){
        System.out.printf("ahhhhhhhhhhh");
        spedometer.setValue(x);
        x+=5;
    }
    
    
}
