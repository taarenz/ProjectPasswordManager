package main.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.application.model.Credenziali;

/**
 * Controller per la funzionalita' Security Check
 */
public class SecurityCheckController{
    // dichiarazione degli oggetti di scena
    @FXML TextField passwordToCheck;
    @FXML AnchorPane resultPane;
    @FXML Label resultSummary;
    @FXML Label resultText;
    @FXML Button check;


    /**
     * Metodo per gestire il controllo della password inserita e mostrare il risultato.
     */
    public void checkPassword(){
        if(passwordToCheck.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non compilati");
            alert.showAndWait();
        }else{
            int result = Credenziali.singlePasswordCheck(passwordToCheck.getText());
            if (result==0){
                resultPane.setStyle("-fx-background-color:" + "brown");
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
