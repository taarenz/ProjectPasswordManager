package main.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.database.DBHandler;
import main.application.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller per la registrazione di un nuovo utente.
 */
public class RegistrazioneController {
    // dichiarazione elementi di scena
    @FXML AnchorPane anchorPane;
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField passwordFieldPassword;
    @FXML PasswordField passwordFieldConferma;
    @FXML Button bottoneConferma;

    /**
     * Metodo per gestire la registrazione dell'utente.
     * Creazione dell'utente nel database.
     * SQLException gestita dal metodo.
     */
    public void confermaRegistrazione() {
        // controllo che tutti i campi siano compilati
        if(campiCheck()){

            Utente u = new Utente(textFieldNomeUtente.getText(), passwordFieldPassword.getText());
            Connection connection = DBHandler.getConnection();

            try {
                String query = "INSERT INTO users (username, password)"+"values (?, ?)";
                PreparedStatement s = connection.prepareStatement(query);
                s.setString(1, u.getNomeUtente());
                s.setString(2, u.getPassword());

                int rs = s.executeUpdate();
                if (rs==1) {
                    System.out.println("User added");
                }

            } catch (SQLException e) {
                DBHandler.queryException();
            }
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

    /**
     * Metodo che controlla che i campi sitoUrl e password siano stati compilati correttamente.
     * @return true se tutti i campi sono compilati correttamente; false altrimenti.
     */
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
        return passwordFieldPassword.getText().equals(passwordFieldConferma.getText());
    }
}
