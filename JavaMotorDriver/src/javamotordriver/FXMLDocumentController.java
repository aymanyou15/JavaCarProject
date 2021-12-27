/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javamotordriver;

import com.fazecast.jSerialComm.SerialPort;
import eu.hansolo.medusa.Gauge;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author ayman elgammal
 */
public class FXMLDocumentController implements Initializable {

    AnchorPane anchor;
    static SerialPort chosenPort;
    @FXML
    Button btn;
    KeyCode key;

    OutputStream out;
    Scanner in;
    @FXML
    public ComboBox portList;
    @FXML
    public Slider verticalSlider;
    @FXML
    public Gauge spedometer;

    int x = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        spedometer.setNeedleColor(Color.RED);
        btn.setText("Start");

        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort portName : portNames) {
            portList.getItems().addAll(portName.getSystemPortName());
        }
    }

    public void btnMouseClicked(MouseEvent mouseEvent) {
        if (btn.getText().equals("Start")) {
            // attempt to connect to the serial port
            chosenPort = SerialPort.getCommPort(portList.getValue().toString());
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            if (chosenPort.openPort()) {
                btn.setText("End");
                portList.setEditable(false);
                out = chosenPort.getOutputStream();
            }

        } else {
            // disconnect from the serial port
            chosenPort.closePort();
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            portList.setEditable(true);
            btn.setText("Start");

        }
    }

    public void onKeyPressed(KeyEvent event) {
        int sliderValue = (int) verticalSlider.getValue();
        // soliman's code
        key = event.getCode();
        switch (key) {
            case UP: 
                try {
                   out.write('f');

              } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            
            break;
            case DOWN: 
                try {
                    out.write('b');
  
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);                
                }
            
            break;
            case LEFT: 
                try {
                    out.write('l');

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            break;
            case RIGHT: 
                try {
                    out.write('r');

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            break;
            
            case B:
                  sliderValue = sliderValue - 5;
                    if (sliderValue < 0) {
                        sliderValue = 0;
                    }
                    verticalSlider.setValue(sliderValue);
                    spedometer.setValue((sliderValue * 50) / 255);
 
            break;
            case S:
          
                    sliderValue = sliderValue + 5;
                    if (sliderValue > 255) {
                        sliderValue = 255;
                    }
                    verticalSlider.setValue(sliderValue);
                    spedometer.setValue((sliderValue * 50) / 255);
 
            break;
            
            
        }

    }

    @FXML
    void dragMouseSlider(MouseEvent event) {

        int sliderValue = (int) verticalSlider.getValue();

        //value to be sent to the Spedo Meter  
        float SpedoMeterValue = (float) (sliderValue * 50) / 255;
        spedometer.setValue(SpedoMeterValue);

        //value to be sent to the Ardiuno 
        int ArdiunoSpeedValue = sliderValue * (-1);

        // System.out.println(sliderValue + " ");
        //label1.setText(verticalSlider.getValue()+" ");
    }
    

}
