package entry;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SerialComm extends Application{
    static SerialPort chosenPort;
    Button connectBtn;
    ComboBox portList;
    int dataSend = 1;
    KeyCode key;
    OutputStream out;
    Scanner in;
    
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
                         out = chosenPort.getOutputStream();
                }
                
                    
                } else {
                        // disconnect from the serial port
                        chosenPort.closePort();
                        try {
                            out.close();
                        } catch (IOException ex) {
                            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
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
                            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                }
            }
            
        });
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
