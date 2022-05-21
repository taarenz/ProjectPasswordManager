package main.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller per la MainApp
 */
public class MainAppController implements Initializable {
    // dichiarazione var statica dell'array list (Observable array list) in cui verranno salvate le credenziali
    public static ObservableList<Credenziali> listaCredenzialiUtente;

    // dichiarazione degli oggetti di scena
    @FXML AnchorPane anchorPane;
    @FXML BorderPane borderPane;
    @FXML Button bottoneSwitchNuovaPassword;
    @FXML Button bottoneSwitchModificaPassword;
    @FXML Button bottoneSwitchVisualizzaPassword;
    @FXML Button bottoneSwitchSecurityCheck;
    @FXML Button bottoneSwitchAbout;
    @FXML Button bottoneLogOut;


    /**
     * Inizializzazione della scena.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaCredenzialiUtente = FXCollections.observableArrayList();
    }

    /**
     * Metodo per il cambio di scena.
     * @param path percorso alla view da caricare.
     * IOException gestita dal metodo.
     */
    // metodi per cambiare l'ancorPane (Main view)
    public void switchScene(String path){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore nella richiesta");
            alert.setHeaderText("Impossibile mostrare i dati richiesti.");
            alert.setContentText("Riavviare il programma e riprovare.");

            alert.showAndWait();
        }

        borderPane.setCenter(root);
    }

    /**
     * Carica scena nuova password.
     */
    public void scenaNuovaPassword(){
        switchScene("/main/application/nuova-password-view.fxml");
    }

    /**
     * Carica scena modifica password.
     */
    public void scenaModificaPassword(){
        switchScene("/main/application/modifica-password-view.fxml");
    }

    /**
     * Carica scena visulizza password.
     */
    public void scenaVisualizzaPassword() throws Exception{
        switchScene("/main/application/visualizza-password-view.fxml");
    }

    /**
     * Carica scena security check.
     */
    public void scenaSecrityCheck(){
        switchScene("/main/application/security-check-view.fxml");
    }

    /**
     * Metodo per la gestione del logout dell'utente.
     */
    public void logOut(ActionEvent event) throws Exception{
        MainApp.loggedUser = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/main/application/login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LOGIN");
        stage.show();
    }
}
