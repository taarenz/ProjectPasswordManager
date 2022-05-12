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
    public void switchAnchorPane(String path) throws Exception{
        Parent root = null;

        root = FXMLLoader.load(getClass().getResource(path));

        borderPane.setCenter(root);
    }

    public void scenaNuovaPassword() throws Exception {
        switchAnchorPane("/main/application/nuova-password-view.fxml");
    }

    public void scenaModificaPassword() throws Exception {
        switchAnchorPane("/main/application/modifica-password-view.fxml");
    }


}
