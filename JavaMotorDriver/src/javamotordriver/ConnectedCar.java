/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamotordriver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Ahmed Yehia
 */

public class ConnectedCar extends Thread{
    DataInputStream instream;
    Group g;
    Color color;
    double startx,starty,angle,r;
    Rectangle car;
    Line last;
    Group live;
    Arc leftarc,rightarc;
    Timeline t;
    int carx,cary;
    double cardiag;
    public static int count = 0;
    public static Text counter = new Text("Connected Cars: 0");
    
    public ConnectedCar(Socket socket,Group grb,Color clr){
        angle = 90;
        r = 1;
        count++;
        Platform.runLater(() -> {
            counter.setText("Connected Cars: "+Integer.toString(count));
            counter.setX(4);
            counter.setY(20);
            counter.setStroke(Color.WHITE);
            g.getChildren().add(counter);
        });
        startx = 200;
        starty = 280;
        g = grb;
        carx = 10;
        cary = 20;
        color = clr;
        cardiag = Math.sqrt(Math.pow(carx,2)+Math.pow(cary,2))/2;
        car = new Rectangle(startx-5,starty-10,carx,cary);
        car.setFill(color);
        last = new Line(startx,starty,startx,starty);
        last.setStroke(color);
        
        live = new Group();
        leftarc = new Arc(startx,starty,20,20,320,80);
        leftarc.setFill(Color.TRANSPARENT);
        leftarc.setStroke(color);
        rightarc = new Arc(startx,starty,20,20,140,80);
        rightarc.setFill(Color.TRANSPARENT);
        rightarc.setStroke(color);
        
        t = new Timeline(
            new KeyFrame(Duration.millis(0), 
                    new KeyValue(leftarc.radiusXProperty(), 20),
                    new KeyValue(leftarc.radiusYProperty(), 20),
                    new KeyValue(leftarc.opacityProperty(), 1),
                    new KeyValue(rightarc.radiusXProperty(),20),
                    new KeyValue(rightarc.radiusYProperty(),20),
                    new KeyValue(rightarc.opacityProperty(),1)
            ),
            new KeyFrame(Duration.millis(500),
                    new KeyValue(leftarc.radiusXProperty(), 40),
                    new KeyValue(leftarc.radiusYProperty(), 40),
                    new KeyValue(leftarc.opacityProperty(), 0),
                    new KeyValue(rightarc.radiusXProperty(),40),
                    new KeyValue(rightarc.radiusYProperty(),40),
                    new KeyValue(rightarc.opacityProperty(),0)
            ),
            new KeyFrame(Duration.millis(1000))
        );
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

        
        live.getChildren().addAll(leftarc,rightarc);
        
        Platform.runLater(() -> {
            g.getChildren().addAll(car,live,last);
        });
        try {
            instream = new DataInputStream(socket.getInputStream());
            start();
        } catch (IOException ex) {
            System.out.println("No Car connection");
        }
    }
    @Override
    public void run(){
        while(true){
            try {
                String incoming = instream.readLine();
                System.out.println(incoming);
                if(incoming == null)
                    break;
                Platform.runLater(() -> {
                    if(incoming.equals("W") || incoming.equals("S")){
                        double posx = startx+r*Math.cos(Math.PI*angle/180)
                                +cardiag*(Math.cos(Math.atan2(cary,carx))-Math.cos(Math.atan2(carx,cary)));
                        double posy = starty-r*Math.sin(Math.PI*angle/180)
                                -2*cardiag*(Math.sin(Math.atan2(cary,carx))-Math.sin(Math.atan2(carx,cary)));
                        car.setX(posx);
                        car.setY(posy);
                        leftarc.setCenterX(posx+5);
                        leftarc.setCenterY(posy+10);
                        rightarc.setCenterX(posx+5);
                        rightarc.setCenterY(posy+10);
                        
                        last.setEndX(startx+r*Math.cos(Math.PI*angle/180));
                        last.setEndY(starty-r*Math.sin(Math.PI*angle/180));
                        if(incoming.equals("W"))
                            r++;
                        else
                            r--;
                    }else{
                        car.setRotate(90-angle);
                        live.setRotate(90-angle);
                        startx = last.getEndX();
                        starty = last.getEndY();
                        r = 1;
                        if(last.getStartX() != last.getEndX() || last.getStartY() != last.getEndY()){
                            last = new Line(startx,starty,startx,starty);
                            last.setStroke(color);
                            g.getChildren().add(last);
                        }
                        if(incoming.equals("A")){
                            angle++;
                        }
                        else if(incoming.equals("D")){
                            angle--;
                        }
                    }
                });
            } catch (IOException ex) {
                System.out.println("Car Connection is closed");
                Platform.runLater(()->{
                    count--;
                    counter.setText("Connected Cars: "+Integer.toString(count));
                    t.stop();
                    Circle ending = new Circle(car.getX()+5,car.getY()+10,2);
                    ending.setFill(color);
                    g.getChildren().add(ending);
                    g.getChildren().removeAll(car,live);
                });
                break;
            }
        }
    }
}

