package main.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.application.model.Utente;

public class RegistrazioneController {
    // dichiarazione elementi di scena
    @FXML AnchorPane anchorPane;
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField passwordFieldPassword;
    @FXML PasswordField passwordFieldConferma;
    @FXML Button bottoneConferma;

    // azione della registrazione
    public void confermaRegistrazione() {
        // controllo che tutti i campi siano compilati
        if(campiCheck()){
            // creazione dell'utente
            Utente u = new Utente(textFieldNomeUtente.getText(), passwordFieldPassword.getText());

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non compilati correttamente.");
            alert.setContentText("Compilare correttamente tutti i campi.");

            alert.showAndWait();
        }
    }

    private boolean campiCheck() {
        if(textFieldNomeUtente.getText().equals("")){
            return false;
        }
        if(passwordFieldPassword.getText().equals("")){
            return false;
        }
        if(passwordFieldConferma.getText().equals("")){
            return false;
        }

        // controllo che le due password siano ugali
        if(!passwordFieldPassword.getText().equals(passwordFieldConferma.getText())){
            return false;
        }

        return true;
    }
}
