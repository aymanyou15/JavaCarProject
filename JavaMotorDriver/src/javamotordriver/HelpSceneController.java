/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javamotordriver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed Yehia
 */
public class HelpSceneController implements Initializable {

    public Button userGuideButton;
    public Button closeButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userGuideButton.setStyle( "-fx-background-color: #800000;" );
        closeButton.setStyle("-fx-background-color: #800000;");
    }    
    @FXML
    void CloseHandler(MouseEvent event) throws IOException {
        Stage ps = (Stage)closeButton.getScene().getWindow();
        ps.close();
    }

    @FXML
    void UserGuideHandler(MouseEvent event) throws IOException {
        Parent nfxml = FXMLLoader.load(getClass().getResource("UserGuide.fxml"));
        Scene nc = new Scene(nfxml);
        Stage ps = (Stage) userGuideButton.getScene().getWindow();

        ps.setScene(nc);
        ps.show();
    }
    
}
