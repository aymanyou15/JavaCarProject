
package spedometer;
import guage.*;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author ayman elgammal
 */
public class SpedoMeterFeature extends Application {
    int x = 0;
    
    @Override
    public void start(Stage primaryStage) {
        Gauge spedoMeter = new Gauge();     // creating an object of Medusa Gauge 
        Button btn = new Button();
        Button btn1 = new Button();
        btn.setText("test spedo up");
        btn1.setText("test spedo down");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {                
                x += 5;
                if (x >= 50)
                    x = 50;
                if (x <= 0)
                    x = 0;
                if (x >= 30){
                    spedoMeter.setTickMarkColor(Color.RED);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.RED);
                    spedoMeter.setValueColor(Color.RED);
                    spedoMeter.setUnitColor(Color.RED); 
                    spedoMeter.setTitleColor(Color.RED);
                }
                if (x < 30){
                    spedoMeter.setTickMarkColor(Color.BLUE);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.BLUE);
                    spedoMeter.setValueColor(Color.WHITE);
                    spedoMeter.setUnitColor(Color.WHITE);  
                    spedoMeter.setTitleColor(Color.WHITE);
                }
                spedoMeter.setValue(x);
            }
        });
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {                
                x -= 5;
                if (x <= 0)
                    x = 0;
                if (x >= 50)
                    x = 50;
                if (x >= 30){
                    spedoMeter.setTickMarkColor(Color.RED);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.RED);
                    spedoMeter.setValueColor(Color.RED);
                    spedoMeter.setUnitColor(Color.RED); 
                    spedoMeter.setTitleColor(Color.RED);
                    spedoMeter.setNeedleColor(Color.BLACK);
                }
                if (x < 30){
                    spedoMeter.setTickMarkColor(Color.BLUE);
                    spedoMeter.setTickLabelColor(Color.WHITE);
                    spedoMeter.setBarColor(Color.BLUE);
                    spedoMeter.setValueColor(Color.WHITE);
                    spedoMeter.setUnitColor(Color.WHITE);  
                    spedoMeter.setTitleColor(Color.WHITE);
                    spedoMeter.setNeedleColor(Color.RED);
                }
                spedoMeter.setValue(x);
            }
        });
        
        spedoMeter.setSkin(new ModernSkin(spedoMeter));
        spedoMeter.setUnit("Km / h");
        spedoMeter.setUnitColor(Color.WHITE);
        spedoMeter.setAnimated(true);
        spedoMeter.setAnimationDuration(250);
        spedoMeter.setDecimals(0);
        spedoMeter.setValueColor(Color.WHITE);
        spedoMeter.setTitle("Speed");
        spedoMeter.setTitleColor(Color.WHITE);
        spedoMeter.setNeedleColor(Color.RED);
        spedoMeter.setSubTitle("ITI");
        spedoMeter.setSubTitleColor(Color.DARKRED);
        spedoMeter.setMaxValue(50);
        spedoMeter.setBarColor(Color.BLUE);
        spedoMeter.setThreshold(30);
        spedoMeter.setThresholdColor(Color.RED);
        spedoMeter.setThresholdVisible(true);
        spedoMeter.setTickLabelColor(Color.WHITE);
        spedoMeter.setTickMarkColor(Color.BLUE);
        spedoMeter.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);        

        
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(spedoMeter, btn, btn1);
        VBox.setVgrow(spedoMeter, Priority.ALWAYS);
        vbox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(vbox, 500, 400);
        
        primaryStage.setTitle("SpedoMeter");
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
