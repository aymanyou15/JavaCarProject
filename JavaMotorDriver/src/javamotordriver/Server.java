/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamotordriver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javamotordriver.ConnectedCar.*;
import java.util.Vector;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 *
 * @author Ahmed Yehia
 */
public class Server extends Application {
    ServerSocket server;
    Socket handler;
    DataInputStream instream;
    Group g;
    Scene scene;
    ConnectedCar cc;
    Vector <Color> colors;
    
    @Override
    public void init(){
        colors = new Vector<Color>();
//        colors.add(Color.valueOf("#3a0ca3"));
//        colors.add(Color.valueOf("#2A9D8F"));
//        colors.add(Color.valueOf("#E9C46A"));
//        colors.add(Color.valueOf("#E76F51"));
//        colors.add(Color.valueOf("#e63946"));
//        colors.add(Color.valueOf("#f72585"));
        colors.add(Color.valueOf("#f72585"));
        colors.add(Color.valueOf("#b5179e"));
        colors.add(Color.valueOf("#7209b7"));
        colors.add(Color.valueOf("#560bad"));
        colors.add(Color.valueOf("#480ca8"));
        colors.add(Color.valueOf("#3a0ca3"));
        colors.add(Color.valueOf("#3f37c9"));
        colors.add(Color.valueOf("#4361ee"));
        colors.add(Color.valueOf("#4895ef"));
        colors.add(Color.valueOf("#4cc9f0"));
        g = new Group();
        scene = new Scene(g,400,300);
        
        Rectangle cir = new Rectangle(0,0,40,5);
        cir.setFill(colors.get(0));
        g.getChildren().add(cir);
        for(int i=1;i<colors.size();i++){
            cir = new Rectangle(i*40,0,40,5);
            cir.setFill(colors.get(i));
            g.getChildren().add(cir);
        }
//        count.setText("Connected Cars: 0");
        
        scene.setFill(Color.valueOf("#000000"));
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        server = new ServerSocket(5005);
        ConnectedCar.counter.setText("Connected Cars: "+Integer.toString(ConnectedCar.count));
        ConnectedCar.counter.setX(4);
        ConnectedCar.counter.setY(20);
        ConnectedCar.counter.setStroke(Color.WHITE);
        g.getChildren().add(ConnectedCar.counter);
        new Thread(() -> {
            while(true){
                try {
                    handler = server.accept();
                    cc = new ConnectedCar(handler,g,colors.get(ConnectedCar.count%colors.size()));
                    
                } catch (IOException ex) {
                    // need to be handled
                }
            }
        }).start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
