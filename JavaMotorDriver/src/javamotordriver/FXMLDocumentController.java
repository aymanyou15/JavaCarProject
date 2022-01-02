/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javamotordriver;

import com.fazecast.jSerialComm.SerialPort;
import eu.hansolo.medusa.Gauge;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    public Gauge spedometer;
    @FXML
    private Gauge rpm;
    @FXML
    private Slider hSlider;
    @FXML
    private Gauge feul;
    @FXML
    private Gauge heat;
    @FXML
    Image picture;
    @FXML
    private ImageView img;
    int x = 0;
    @FXML
    private MediaView mv;
    MediaPlayer mp;
    Media me;
    @FXML
    public MenuBar menuBar;
    @FXML
    public MenuItem About;
    @FXML
    public MenuItem UserGuide;
    @FXML
    public Menu helpMenu;
    @FXML
    public Menu comMenu;
    
    int commValue = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btn.setShape(new Circle(100));
        btn.setText("Start");
        btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                + "-fx-text-align: center;");

        hSlider.setStyle("-fx-control-inner-background: #293d3d;");
        helpMenu.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 18px;");
        comMenu.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 18px;");
        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort portName : portNames) {
            portList.getItems().addAll(portName.getSystemPortName());

        }
    }

    public void btnMouseClicked(MouseEvent mouseEvent) {
        if (btn.getText().equals("Start")) {
            // attempt to connect to the serial port
            try {
                chosenPort = SerialPort.getCommPort(portList.getValue().toString());
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

                if (chosenPort.openPort()) {
                    btn.setText("End");
                    btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #DB341D;" + "-fx-font-weight: bold;"
                                  + "-fx-text-align: center;");
                    portList.setEditable(false);
                    out = chosenPort.getOutputStream();
                }
                else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("please, check your device");
                    alert.show();
                }
            } catch (NullPointerException ex) {

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
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                + "-fx-text-align: center;");

        }
    }

    public void onKeyPressed(KeyEvent event) {
        int sliderValue = (int) hSlider.getValue();
        float RPMValue;
        hSlider.setFocusTraversable(false);
        portList.setFocusTraversable(false);
        // soliman's code
        try {
            key = event.getCode();
        } catch (NullPointerException ex) {

        }
        
        if (key == KeyCode.UP){
            commValue = (int) (0 + (sliderValue/12.75)); 
        }
        
        if (key == KeyCode.DOWN){
            commValue = (int) (22 + (sliderValue/12.75)); 
        }
        
        if (key == KeyCode.LEFT){
            commValue = (int) (44 + (sliderValue/12.75)); 
        }
        
        if (key == KeyCode.RIGHT){
            commValue = (int) (66 + (sliderValue/12.75)); 
        }
        
        if (key == KeyCode.W){
            sliderValue = sliderValue + 5;
            if (sliderValue < 0) {
                sliderValue = 0;
            }
            else if(sliderValue > 255){
                sliderValue = 255;
            }
            hSlider.setValue(sliderValue);
            spedometer.setValue((sliderValue * 50) / 255);
            RPMValue = (float) ((sliderValue * 5.5) / 60);
            rpm.setValue(RPMValue);
        }
        
        if (key == KeyCode.S){
            sliderValue = sliderValue - 5;
            if (sliderValue < 0) {
                sliderValue = 0;
            }
            else if(sliderValue > 255){
                sliderValue = 255;
            }
            hSlider.setValue(sliderValue);
            spedometer.setValue((sliderValue * 50) / 255);
            RPMValue = (float) ((sliderValue * 5.5) / 60);
            rpm.setValue(RPMValue);
        }
        
        if (key == KeyCode.E){
            commValue += 100;
        }
        
        try {
            out.write(commValue);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    @FXML
    void onKeyReleased(KeyEvent event) {
        try{
            key = event.getCode();
        } catch (NullPointerException ex){
            
        }
        
        if (key == KeyCode.E){
            commValue -= 100;
        }
        else{
            try {
                out.write(205);

            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void dragMouseSlider(MouseEvent event) {

        int sliderValue = (int) hSlider.getValue();

        //value to be sent to the Spedo Meter  
        float SpedoMeterValue = (float) (sliderValue * 50) / 255;
        spedometer.setValue(SpedoMeterValue);

        //value to be sent to the RPM guage   (RPM = %duty-cycle * 5.5) 
        //duty-cycle=slidervalue/1(s)*100
        float RPMValue = (float) ((sliderValue * 5.5) / 60);
        rpm.setValue(RPMValue);

        //value to be sent to the Feul consumption 
        int feulValue = sliderValue / 10;
        feul.setValue(feulValue);

        //value to be sent to the Feul consumption 
        int heatValue = sliderValue / 10;
        heat.setValue(heatValue);

    }

    @FXML
    void AboutHandler(ActionEvent event) throws IOException {
        Parent nfxml = FXMLLoader.load(getClass().getResource("HelpScene.fxml"));
        Scene nc = new Scene(nfxml);
        Stage ps = (Stage) menuBar.getScene().getWindow();
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(ps);
        popup.setScene(nc);
        popup.setResizable(false);
        popup.show();
    }

    @FXML
    void UserGuideHandler(ActionEvent event) throws IOException {
        Parent nfxml = FXMLLoader.load(getClass().getResource("UserGuide.fxml"));
        Scene nc = new Scene(nfxml);
        Stage ps = (Stage) menuBar.getScene().getWindow();
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(ps);
        popup.setScene(nc);
        popup.setResizable(false);
        popup.show();
    }
    
    
      @FXML
    void btnOnMouseEntered(MouseEvent event) {
        if (btn.getText().equals("Start")){
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #00B200;" + "-fx-font-weight: bold;"
                           + "-fx-text-align: center;");
        }
        
        else if (btn.getText().equals("End")){
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #FF0000;" + "-fx-font-weight: bold;"
                           + "-fx-text-align: center;");
        }
    }

    @FXML
    void btnOnMouseExited(MouseEvent event) {
        if (btn.getText().equals("Start")){
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                           + "-fx-text-align: center;");
        }
        
        else if (btn.getText().equals("End")){
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #DB341D;" + "-fx-font-weight: bold;"
                           + "-fx-text-align: center;");
        }

    }
}
