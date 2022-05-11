package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.application.model.Utente;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    // dichiarazione variabili locali
    Utente utenteSalvato;

    // dichiarazione dei componenti di Scena
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField textFieldPassword;
    @FXML Button loginButton;
    @FXML Button registratiButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // caricamento password
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/txtFile/utente_criptato.txt");
            ObjectInputStream os = new ObjectInputStream(fis);

            utenteSalvato = new Utente((Utente) os.readObject());

            os.close();

            System.out.println("OKKKK");

        } catch (Exception e){
            System.out.println("ERRORE FILE");
        }

    }

    public void loginAction(){
        if(utenteSalvato.equals(new Utente(textFieldNomeUtente.getText(),textFieldPassword.getText()))){
            System.out.println("OK");
        } else {
            System.out.println("LOGIN FAILURE");
        }
    }


}