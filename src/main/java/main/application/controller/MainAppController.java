package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class MainAppController {
    @FXML AnchorPane anchorPane;
    @FXML BorderPane borderPane;
    @FXML Button bottoneSwitchNuovaPassword;
    @FXML Button bottoneSwitchModificaPassword;
    @FXML Button bottoneSwitchVisualizzaPassword;
    @FXML Button bottoneSwitchSecurityCheck;
    @FXML Button bottoneSwitchAbout;

    // metodi per cambiare l'ancorPane (Main view)
    public void switchScene(String path) throws Exception {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (Exception e){
            System.out.println("ERRORE NEL CARICAMENTO");
        }

        borderPane.setCenter(root);
    }

    public void scenaNuovaPassword() throws Exception {
        switchScene("/main/application/nuova-password-view.fxml");
    }
    public void scenaModificaPassword() throws Exception {
        switchScene("/main/application/modifica-password-view.fxml");
    }



}
