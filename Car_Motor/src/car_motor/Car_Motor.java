/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package car_motor;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Car_Motor extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       Group root = new Group();
       Gauge spedoMeter = new Gauge(); 
  
  
        // Creates a slider
        Slider slider = new Slider(0, 255, 0);
         //TextField text = new TextField();
     
        // to gey the slider value 
        double value = slider.getValue();
        String s=String.valueOf(value);  
       // text.setText(s);
        
         slider.valueProperty().addListener(
             new ChangeListener<Number>() {
  
            @Override
            public void changed(ObservableValue <? extends Number > 
                      observable, Number oldValue, Number newValue)
            {
  
                //String x=String.valueOf(newValue);
                //String x=String.valueOf(newValue);
                
              
                /*double x = newValue.doubleValue();
                //x += 5;
                if (x >= 50)
                    x = 50;
                if (x <= 0)
                    x = 0;
                if (x >= 30){
                    spedoMeter.setTickMarkColor(Color.RED);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.RED);
                    spedoMeter.setValueColor(Color.RED);
                    spedoMeter.setUnitColor(Color.RED); 
                    spedoMeter.setTitleColor(Color.RED);
                }
                if (x < 30){
                    spedoMeter.setTickMarkColor(Color.BLUE);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.BLUE);
                    spedoMeter.setValueColor(Color.WHITE);
                    spedoMeter.setUnitColor(Color.WHITE);  
                    spedoMeter.setTitleColor(Color.WHITE);
                }
                spedoMeter.setValue(x);
            
     
        
                      
            
                if (x <= 0)
                    x = 0;
                if (x >= 50)
                    x = 50;
                if (x >= 30){
                    spedoMeter.setTickMarkColor(Color.RED);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.RED);
                    spedoMeter.setValueColor(Color.RED);
                    spedoMeter.setUnitColor(Color.RED); 
                    spedoMeter.setTitleColor(Color.RED);
                    spedoMeter.setNeedleColor(Color.BLACK);
                }
                if (x < 30){
                    spedoMeter.setTickMarkColor(Color.BLUE);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.BLUE);
                    spedoMeter.setValueColor(Color.WHITE);
                    spedoMeter.setUnitColor(Color.WHITE);  
                    spedoMeter.setTitleColor(Color.WHITE);
                    spedoMeter.setNeedleColor(Color.RED);
                }
                spedoMeter.setValue(x);

                
                //text.setText(x);*/
                
            }  

        });
         
          spedoMeter.setSkin(new ModernSkin(spedoMeter));
        spedoMeter.setUnit("Km / h");
        spedoMeter.setUnitColor(Color.WHITE);
        spedoMeter.setAnimated(true);
        spedoMeter.setAnimationDuration(250);
        spedoMeter.setDecimals(0);
        spedoMeter.setValueColor(Color.WHITE);
        spedoMeter.setTitle("Speed");
        spedoMeter.setTitleColor(Color.WHITE);
        spedoMeter.setNeedleColor(Color.RED);
        spedoMeter.setSubTitle("ITI");
        spedoMeter.setSubTitleColor(Color.DARKRED);
        spedoMeter.setMaxValue(50);
        spedoMeter.setBarColor(Color.BLUE);
        spedoMeter.setThreshold(30);
        spedoMeter.setThresholdColor(Color.RED);
        spedoMeter.setThresholdVisible(true);
        spedoMeter.setTickLabelColor(Color.WHITE);
        spedoMeter.setTickMarkColor(Color.BLUE);
        spedoMeter.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);        

        
        // enable the marks
        slider.setShowTickMarks(true);
  
        // enable the Labels
        slider.setShowTickLabels(true);
  
        // set Major tick unit
        slider.setMajorTickUnit(20f);
  
        // set Minor tick unit
        slider.setMinorTickCount(1);
        
        // sets the value of the property
        // blockIncrement
       // slider.setBlockIncrement(0.1f);
  
       //Setting the dimensions of the slider
      slider.setPrefWidth(450);
      slider.setPrefHeight(150);
      
      spedoMeter.setPrefHeight(500);  
      spedoMeter.setPrefWidth(300);
      
        root.getChildren().add(slider);
        //root.getChildren().add(text);
        root.getChildren().add(spedoMeter);
        
        // create a Scene
        Scene scene = new Scene(root, 600, 400);
        
        primaryStage.setScene(scene);
        // set title of the frame
        primaryStage.setTitle("Slider");
        // display
        primaryStage.show();
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
