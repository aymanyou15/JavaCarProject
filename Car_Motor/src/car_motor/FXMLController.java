/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

 package car_motor;

import eu.hansolo.medusa.Gauge;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class FXMLController {

   @FXML private MediaView mv;
    
    MediaPlayer mp;
    Media me;

    @FXML
    private ToggleGroup direction;

    @FXML
    private Slider slider;
    @FXML
    private Slider slider1;

     @FXML
    private Label label;
     @FXML
    private Label label1;
    @FXML
    private Gauge spedometer;
    
    @FXML
    private CheckBox checkBox;
    
    @FXML
    private Button b;

    @FXML
    public void ad(MouseEvent event) {

        System.out.println("clockwise");
    }

    @FXML
   public  void d(MouseEvent event) {

        System.out.println("Anti-clockwise");
    }

    @FXML
   void updateSpedoMeter(MouseEvent event) {
        
      /*  slider.valueProperty().addListener(
             new ChangeListener<Number>() {
  
            @Override
            public void updateSpedoMeter(ObservableValue <? extends Number > 
                      observable, Number oldValue, Number newValue)
            {
                
                label1.setText(String.valueOf(newValue));
            }
            
                  

        });*/
       //label1.setText(String.valueOf(slider));
        int sliderValue = (int) slider.getValue();
   // System.out.println(sliderValue + " ");
    label1.setText(slider.getValue()+" ");
    spedometer.setValue(sliderValue);
    
    
    /*slider1.valueProperty().addListener(new InvalidationListener() {
            
            public void invalidated(Observable observable) {
                mp.setVolume(slider1.getValue() / 100);
                
            }

            @Override
            public void invalidated(javafx.beans.Observable observable) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });*/
   }
       
    
    
    
    @FXML
    void click(MouseEvent event) {
        b.setText("okkk");
        System.out.println("Anti-clockwise");

    }
    
    @FXML
    void ch(ActionEvent event) {

           if (checkBox.isSelected()) {
                label.setText("ClockWise");
                /*
                send data via bluetooth
                */
            } else {
                label.setText("Anti-ClockWise");
                /*
                send data via bluetooth
                */
    }

    }
    public void initialize(URL location, ResourceBundle resources) {
        String path = new File("C:\\Users\\DELL\\Music\\media//HELLMA~1.mp3").getAbsolutePath();
        
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        //mp.setAutoPlay(true);
        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        
        slider1.setValue(mp.getVolume() * 100); // 1.0 = max 0.0 = min
       
    }
 
   


}
