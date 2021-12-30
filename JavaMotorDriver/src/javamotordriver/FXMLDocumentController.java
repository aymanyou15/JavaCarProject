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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    @FXML private MediaView mv;
    MediaPlayer mp;
    Media me;
    @FXML
    public MenuBar menuBar;
    @FXML
    public MenuItem About;
    @FXML
    public MenuItem UserGuide;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //spedometer.setNeedleColor(Color.RED);
        /*String path = new File("test.mp4").getAbsolutePath();
        
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        //mp.setAutoPlay(true);
        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));*/
        btn.setShape(new Circle(100));
        btn.setText("Start");
        btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                + "-fx-text-align: center;");
        
        hSlider.setStyle( "-fx-control-inner-background: #293d3d;" );
        

        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort portName : portNames) {
            portList.getItems().addAll(portName.getSystemPortName());
            
        
        
       /* hSlider.setValue(mp.getVolume() * 100); // 1.0 = max 0.0 = min
        hSlider.valueProperty().addListener((Observable observable) -> {
            mp.setVolume(hSlider.getValue() / 100);
            });*/
        
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
        int sliderValue = (int) hSlider.getValue();
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
                    hSlider.setValue(sliderValue);
                    spedometer.setValue((sliderValue * 50) / 255);
 
            break;
            case S:
          
                    sliderValue = sliderValue + 5;
                    if (sliderValue > 255) {
                        sliderValue = 255;
                    }
                    hSlider.setValue(sliderValue);
                    spedometer.setValue((sliderValue * 50) / 255);
 
            break;
            
            
        }

    }

    @FXML
    void dragMouseSlider(MouseEvent event) {

        int sliderValue = (int) hSlider.getValue();

        //value to be sent to the Spedo Meter  
        float SpedoMeterValue = (float) (sliderValue * 50) / 255;
        spedometer.setValue(SpedoMeterValue);

        //value to be sent to the Ardiuno 
        int ArdiunoSpeedValue = sliderValue * (-1);
        
         //value to be sent to the RPM guage   (RPM = %duty-cycle * 5.5) 
         //duty-cycle=slidervalue/1(s)*100
        float RPMValue = (float) ((sliderValue * 5.5) / 60);
        rpm.setValue(RPMValue);
        
        //value to be sent to the Feul consumption 
        int feulValue = sliderValue/10 ;
        feul.setValue(feulValue);
        
        //value to be sent to the Feul consumption 
        int heatValue =  sliderValue/10 ;
       heat.setValue(heatValue);
       
        
       /* mp.play();
        mp.setRate(1);*/

        // System.out.println(sliderValue + " ");
        //label1.setText(verticalSlider.getValue()+" ");
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
        popup.show();
    }
}
