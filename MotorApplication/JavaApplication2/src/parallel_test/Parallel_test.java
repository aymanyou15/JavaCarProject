/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel_test;

import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed Soliman
 */
public class Parallel_test extends Application {
    static SerialPort chosenPort;
    Button connectBtn;
    ComboBox portList;
    
    @Override
    public void init ()
    {
        connectBtn = new Button("Connect");
        portList = new ComboBox();
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        connectBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if(connectBtn.getText().equals("Connect")) {
                // attempt to connect to the serial port
                chosenPort = SerialPort.getCommPort(portList.getValue().toString());
                chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                if(chosenPort.openPort()) {
                        connectBtn.setText("Disconnect");
                        portList.setEditable(false);
                }

                // create a new thread that listens for incoming text and populates the graph
                Thread thread = new Thread(){
                        @Override public void run() {
                                Scanner scanner = new Scanner(chosenPort.getInputStream());
                                while(scanner.hasNextLine()) {
                                        try {
                                                String line = scanner.nextLine();
                                                System.out.println(line);
                                        } catch(Exception e) {}
                                }
                                scanner.close();
                        }
                    };
                    thread.start();
            } else {
                    // disconnect from the serial port
                    chosenPort.closePort();
                    portList.setEditable(true);
                    connectBtn.setText("Connect");

            }
            }
        });
        
        // populate the drop-down box
	SerialPort[] portNames = SerialPort.getCommPorts();
        for (SerialPort portName : portNames) {
            portList.getItems().addAll(portName.getSystemPortName());
        }
                               
        
        HBox buttonBox = new HBox(5,connectBtn,portList);
        
        
        Scene scene = new Scene(buttonBox, 300, 250);
        
        primaryStage.setTitle("connection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}


