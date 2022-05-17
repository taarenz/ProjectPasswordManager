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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

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


    // metodo initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaCredenzialiUtente = FXCollections.observableArrayList();
    }

    // metodi per cambiare l'ancorPane (Main view)
    public void switchScene(String path) throws Exception {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (Exception e){
            System.out.println(e);
        }

        borderPane.setCenter(root);
    }

    public void scenaNuovaPassword() throws Exception {
        switchScene("/main/application/nuova-password-view.fxml");
    }
    public void scenaModificaPassword() throws Exception {
        switchScene("/main/application/modifica-password-view.fxml");
    }

    public void scenaVisualizzaPassword() throws Exception{
        switchScene("/main/application/visualizza-password-view.fxml");
    }

    public void logOut(ActionEvent event) throws Exception{
        MainApp.loggedUser = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/main/application/login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LOGIN");
        stage.show();
        // print sul terminale
        System.out.println(MainApp.loggedUser);
    }
}
