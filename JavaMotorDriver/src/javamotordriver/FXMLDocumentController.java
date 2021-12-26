/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javamotordriver;

import com.fazecast.jSerialComm.SerialPort;
import eu.hansolo.medusa.Gauge;
import java.awt.Button;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    Button btn;
    KeyCode key;
    OutputStream out;
    Scanner in;

    public Slider verticalSlider;

    public Gauge spedometer;

    int x = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        spedometer.setNeedleColor(Color.RED);
    }

    public void btnMouseClicked(MouseEvent mouseEvent) {
        x += 5;
        if (spedometer.getValue() >= 50) {
            x = 50;
        }
        if (spedometer.getValue() < 0) {
            x = 0;
        }
        if (spedometer.getValue() >= 30) {
            spedometer.setTickMarkColor(Color.RED);
            spedometer.setTickLabelColor(Color.WHITE);
            spedometer.setBarColor(Color.RED);
            spedometer.setValueColor(Color.RED);
            spedometer.setUnitColor(Color.RED);
            spedometer.setTitleColor(Color.RED);
        }
        if (spedometer.getValue() < 30) {
            spedometer.setTickMarkColor(Color.BLUE);
            spedometer.setTickLabelColor(Color.WHITE);
            spedometer.setBarColor(Color.BLUE);
            spedometer.setValueColor(Color.WHITE);
            spedometer.setUnitColor(Color.WHITE);
            spedometer.setTitleColor(Color.WHITE);
        }
        spedometer.setValue(x);
    }

    public void onKeyPressed(KeyEvent event) {
        // soliman's code
        
        key = event.getCode();
                switch(key)
                {
                    case UP:
                {
                    try {
                        out.write(1);
                        try {
                        out.close();
                        in = new Scanner (chosenPort.getInputStream());
                        System.out.println(in.nextLine());
                        in.close();
                        out = chosenPort.getOutputStream();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                        break;
                    case DOWN:
                {
                    try {
                        out.write(0);
                        try {
                        out.close();
                        in = new Scanner (chosenPort.getInputStream());
                        System.out.println(in.nextLine());
                        in.close();
                        out = chosenPort.getOutputStream();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                    case LEFT:
                {
                    try {
                        out.write(2);
                        try {
                        out.close();
                        in = new Scanner (chosenPort.getInputStream());
                        System.out.println(in.nextLine());
                        in.close();
                        out = chosenPort.getOutputStream();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                    case RIGHT:
                {
                    try {
                        out.write(3);
                        try {
                        out.close();
                        in = new Scanner (chosenPort.getInputStream());
                        System.out.println(in.nextLine());
                        in.close();
                        out = chosenPort.getOutputStream();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                }
    }

}
