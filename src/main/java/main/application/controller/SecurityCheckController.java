package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.application.database.DBHandler;
import main.application.model.Credenziali;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SecurityCheckController implements Initializable {
    // dichiarazione degli oggetti di scena
    @FXML Parent root;
    @FXML TextField passwordToCheck;
    @FXML AnchorPane resultPane;
    @FXML Label resultSummary;
    @FXML Label resultText;
    @FXML Button check;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultSummary.setText("");
    }

    // password check
    public void checkPassword(){
        if(passwordToCheck.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non compilati");
            alert.showAndWait();
        }else{
            int result = Credenziali.singlePasswordCheck(passwordToCheck.getText());
            if (result==0){
                resultPane.setStyle("-fx-background-color:" + "#7a1414");
                resultSummary.setText("ALTAMENTE CRITICA");
                resultText.setText("Cambia immediatamente questa password. Ci vogliono pochi secondi per trovarla");
            }else if (result<10){
                resultPane.setStyle("-fx-background-color:" + "red");
                resultSummary.setText("CRITICA");
                resultText.setText("Cambia questa password: presenta molte vulnerabilità");
            }else if (result<18){
                resultPane.setStyle("-fx-background-color:" + "rgba(255,184,54,0.91)");
                resultSummary.setText("POTENZIALMENTE A RISCHIO");
                resultText.setText("Questa password presenta delle vulnerabilità, ma potrebbe volerci molto per trovarla");
            }else{
                resultPane.setStyle("-fx-background-color:" + "green");
                resultSummary.setText("OTTIMA");
                resultText.setText("Questa password è molto sicura. Servono millenni per trovarla ;)");
            }
        }
    }
}
