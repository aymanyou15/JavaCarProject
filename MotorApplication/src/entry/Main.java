package entry;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class Main extends Application{
    public Button start;
    public Button stop;
    public CheckBox direction;
    @Override
    public void init() {
        start = new Button("Start");
        stop = new Button("Stop");
        direction = new CheckBox("Direction");
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        start.setOnAction(() -> {
            // start
        });
        stop.setOnAction(() -> {
            // start
        });
    }
    
}
