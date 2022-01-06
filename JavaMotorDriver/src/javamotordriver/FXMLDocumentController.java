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
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    AnchorPane anchor;
    static SerialPort chosenPort;
    public Socket socket;
    public PrintStream dos;

    @FXML
    Button btn;
    KeyCode key;

    OutputStream out;
    Scanner in;
    @FXML
    public ComboBox<String> portList;

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

    int x = 0;

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
    private Line line;
    Timeline timeline;

    @FXML
    private Polygon upArrow;

    @FXML
    private Polygon downArrow;

    @FXML
    private Polygon rightArrow;

    @FXML
    private Polygon leftArrow;

    int commValue = 0;

    int buzz = 0;

    ObservableList<String> portListVector = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // socket connection with server
        try {
            socket = new Socket("127.0.0.1",5005);
            dos = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("No Server to Connect to");
        }
        btn.setShape(new Circle(100));
        btn.setText("Start");
        btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                + "-fx-text-align: center;");

        portList.setStyle("-fx-background-color: rgba(41, 61, 61, 0.1);" + "-fx-text-align: center;");
        portList.setValue("");

        hSlider.setStyle("-fx-control-inner-background: #293d3d;");
        helpMenu.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 18px;");
        line.getStrokeDashArray().setAll(25d, 20d, 5d, 20d);
        line.setStrokeWidth(2);

        Thread portCheck = new Thread(() -> {
            while (true) {
                try {
                    detectPorts();

                } catch (Exception ex) {

                }
            }
        });
        portCheck.setDaemon(true);
        portCheck.start();
    }

    public void detectPorts() throws InterruptedException {
        // populate the drop-down box       

        SerialPort[] portNames = SerialPort.getCommPorts();
        portListVector.clear();
        for (SerialPort portName : portNames) {
            portListVector.addAll(portName.getSystemPortName());
        }
        if (portListVector.size() != portList.getItems().size()) {
            Platform.runLater(() -> {
                portList.getItems().clear();
                portList.getItems().addAll(portListVector);
            });
        }

        Thread.sleep(1000);

    }

    public void btnMouseClicked(MouseEvent mouseEvent) {
        if (btn.getText().equals("Start")) {
            // attempt to connect to the serial port
            try {

                chosenPort = SerialPort.getCommPort(portList.getValue().toString());
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

                if (portList.getValue().equals("")) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("please, choose a port from the comboBox");
                    alert.show();
                } else if (chosenPort.openPort()) {
                    final double maxOffset
                            = line.getStrokeDashArray().stream()
                                    .reduce(
                                            0d,
                                            (a, b) -> a + b
                                    );

                    timeline = new Timeline(
                            new KeyFrame(
                                    Duration.ZERO,
                                    new KeyValue(
                                            line.strokeDashOffsetProperty(),
                                            0,
                                            Interpolator.LINEAR
                                    )
                            ),
                            new KeyFrame(
                                    Duration.seconds(1),
                                    new KeyValue(
                                            line.strokeDashOffsetProperty(),
                                            maxOffset,
                                            Interpolator.LINEAR
                                    )
                            )
                    );
                    // timeline.setAutoReverse(true);
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();

                    btn.setText("End");
                    btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #DB341D;" + "-fx-font-weight: bold;"
                            + "-fx-text-align: center;");

                    portList.setEditable(false);
                    new Thread(() -> {
                        out = chosenPort.getOutputStream();
                    }).start();

                    //timeline.stop();
                }

            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("please, check your connection");
                alert.show();
            }

        } else {
            // disconnect from the serial port
            chosenPort.closePort();
            try {
                out.close();
            } catch (IOException ex) {

            }
            portList.setEditable(false);
            portList.setValue("");
            btn.setText("Start");
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                    + "-fx-text-align: center;");
            timeline.stop();
            hSlider.setValue(0);
            spedometer.setValue(0);
            rpm.setValue(0);
        }
    }

    public void onKeyPressed(KeyEvent event) {
        int sliderValue = (int) hSlider.getValue();
        float RPMValue;

//        // soliman's code
        try {
            key = event.getCode();
            // send the key pressed to the server
            dos.println(key);
        } catch (NullPointerException ex) {

        }
        if (key == KeyCode.W || key == KeyCode.S || key == KeyCode.D || key == KeyCode.A
                || key == KeyCode.O || key == KeyCode.K || key == KeyCode.L) {

            if (key == KeyCode.W) {
                commValue = (int) (0 + (sliderValue / 12.75));
                upArrow.setStyle("-fx-fill: #31D9C5;");
                /* downArrow.setStyle("-fx-fill: #040404;");
                rightArrow.setStyle("-fx-fill: #040404;");
                leftArrow.setStyle("-fx-fill: #040404;");*/
            }

            if (key == KeyCode.S) {
                hSlider.setValue(hSlider.getValue());
                commValue = (int) (22 + (sliderValue / 12.75));
                downArrow.setStyle("-fx-fill:  #31D9C5;");
                /*upArrow.setStyle("-fx-fill: #010425;");
                rightArrow.setStyle("-fx-fill: #010425;");
                leftArrow.setStyle("-fx-fill: #010425;");*/
            }

            if (key == KeyCode.A) {
                hSlider.setValue(hSlider.getValue());
                commValue = (int) (44 + (sliderValue / 12.75));
                leftArrow.setStyle("-fx-fill:  #31D9C5;");
                /* downArrow.setStyle("-fx-fill: #010425;");
                rightArrow.setStyle("-fx-fill: #010425;");
                upArrow.setStyle("-fx-fill: #010425;");*/
            }

            if (key == KeyCode.D) {
                hSlider.setValue(hSlider.getValue());
                commValue = (int) (66 + (sliderValue / 12.75));
                rightArrow.setStyle("-fx-fill:  #31D9C5;");
                /* downArrow.setStyle("-fx-fill: #010425;");
                upArrow.setStyle("-fx-fill: #010425;");
                leftArrow.setStyle("-fx-fill: #010425;");*/
            }

            if (key == KeyCode.O) {
                sliderValue = sliderValue + 5;
                if (sliderValue < 0) {
                    sliderValue = 0;
                } else if (sliderValue > 255) {
                    sliderValue = 255;
                }
                hSlider.setValue(sliderValue);
                spedometer.setValue((sliderValue * 50) / 255);
                RPMValue = (float) ((sliderValue * 5.5) / 60);
                rpm.setValue(RPMValue);
            }

            if (key == KeyCode.L) {
                sliderValue = sliderValue - 5;
                if (sliderValue < 0) {
                    sliderValue = 0;
                } else if (sliderValue > 255) {
                    sliderValue = 255;
                }
                hSlider.setValue(sliderValue);
                spedometer.setValue((sliderValue * 50) / 255);
                RPMValue = (float) ((sliderValue * 5.5) / 60);
                rpm.setValue(RPMValue);
            }

            if (key == KeyCode.K) {
                switch (buzz) {
                    case 0:
                        buzz = 1;
                        try {
                            out.write(100);

                        } catch (IOException | NullPointerException ex) {
                            // handle the input output exception
                            // the exception is occured if the keys are pressed and there is no stable connection
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("Please, connect the arduino first");
                            alert.show();

                            rightArrow.setStyle("-fx-fill: #010425;");
                            downArrow.setStyle("-fx-fill: #010425;");
                            upArrow.setStyle("-fx-fill: #010425;");
                            leftArrow.setStyle("-fx-fill: #010425;");
                            spedometer.setValue(0);
                            rpm.setValue(0);
                            hSlider.setValue(0);
                        }
                        break;
                    case 1:
                        buzz = 0;
                        try {
                            out.write(120);

                        } catch (IOException | NullPointerException ex) {
                            // handle the input output exception
                            // the exception is occured if the keys are pressed and there is no stable connection
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("Please, connect the arduino first");
                            alert.show();

                            rightArrow.setStyle("-fx-fill: #010425;");
                            downArrow.setStyle("-fx-fill: #010425;");
                            upArrow.setStyle("-fx-fill: #010425;");
                            leftArrow.setStyle("-fx-fill: #010425;");
                            spedometer.setValue(0);
                            rpm.setValue(0);
                            hSlider.setValue(0);
                        }
                        break;
                }
            }

            try {
                out.write(commValue);

            } catch (IOException | NullPointerException ex) {
                // handle the input output exception
                // the exception is occured if the keys are pressed and there is no stable connection
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please, connect the arduino first");
                alert.show();

                rightArrow.setStyle("-fx-fill: #010425;");
                downArrow.setStyle("-fx-fill: #010425;");
                upArrow.setStyle("-fx-fill: #010425;");
                leftArrow.setStyle("-fx-fill: #010425;");
                spedometer.setValue(0);
                rpm.setValue(0);
                hSlider.setValue(0);
            }
        }
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
        if (key == KeyCode.W || key == KeyCode.S || key == KeyCode.D || key == KeyCode.A
                || key == KeyCode.O || key == KeyCode.L || key == KeyCode.K) {
            try {
                out.write(205);
                upArrow.setStyle("-fx-background-color: #010425;");
                downArrow.setStyle("-fx-background-color: #010425;");
                rightArrow.setStyle("-fx-background-color: #010425;");
                leftArrow.setStyle("-fx-background-color: #010425;");

            } catch (Exception ex) {

            }
        }
    }

    @FXML
    void dragMouseSlider(MouseEvent event
    ) {

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
    void btnOnMouseEntered(MouseEvent event
    ) {
        if (btn.getText().equals("Start")) {
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #00B200;" + "-fx-font-weight: bold;"
                    + "-fx-text-align: center;");
        } else if (btn.getText().equals("End")) {
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #FF0000;" + "-fx-font-weight: bold;"
                    + "-fx-text-align: center;");
        }
    }

    @FXML
    void btnOnMouseExited(MouseEvent event
    ) {
        if (btn.getText().equals("Start")) {
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #3385ff;" + "-fx-font-weight: bold;"
                    + "-fx-text-align: center;");
        } else if (btn.getText().equals("End")) {
            btn.setStyle("-fx-font-size: 25px;" + "-fx-background-color: #DB341D;" + "-fx-font-weight: bold;"
                    + "-fx-text-align: center;");
        }

    }

    @FXML
    void refreshOnMousePressed(MouseEvent event
    ) {

    }
}
