/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel_test;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.RenderingHints.Key;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed Soliman
 */
public class Parallel_test extends Application implements EventHandler<KeyEvent> {
    static SerialPort chosenPort;
    Button connectBtn;
    ComboBox portList;
    String dataSend,dataRecieve;
    KeyCode keyCod;
    
    
    
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
              /*  Thread thread = new Thread(){
                        @Override public void run() {
                                Scanner scanner = new Scanner(chosenPort.getInputStream());
                                while(scanner.hasNextLine()) {
                                        try {
                                                String line = scanner.nextLine();
                                                System.out.println(line);
                                        } catch(Exception e) {}
                                }
                                scanner.close();
                                try {Thread.sleep(500); } catch(Exception e) {}
                        }
                    };
                    thread.start();*/
                    
                    // create a new thread for sending data to the arduino
                    Thread threadSend = new Thread(){
                        @Override public void run() {
                                // wait after connecting, so the bootloader can finish
                            try {Thread.sleep(100); } catch(Exception e) {}
                                System.out.println("A7eeh3");
                            // enter an infinite loop that sends text to the arduino
                            PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                            Scanner scanner = new Scanner(chosenPort.getInputStream());
                                
                            while(true) {
                                    System.out.println("A7eeh2");
                                    output.print("Hellow");
                                    System.out.println("A7eeh1");
                                    output.flush();
                                    if  (scanner.hasNextLine()) {
                                        try {
                                                String line = scanner.nextLine();
                                                System.out.println(line);
                                        } catch(Exception e) {}
                                    }
                                    scanner.close();
                                    try {Thread.sleep(100); } catch(Exception e) {}
                            }
                        }
                    };
                    threadSend.start();
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
        scene.setOnKeyPressed(this);
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

    @Override
    public void handle(KeyEvent event) {
        
        keyCod = event.getCode();
        
        switch(keyCod)
        {
            case UP:
                dataSend = "f";
                System.out.println("a7eeha");
                break;
            case DOWN:
                dataSend = "b";
                System.out.println("a7eeh2");
                break;
            case LEFT:
                dataSend = "l";
                System.out.println("a7eeeh3");
                break;
            case RIGHT:
                dataSend = "r";
                break;
        }
    }
    
}


