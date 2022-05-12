package main.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.application.model.Utente;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    // dichiarazione oggetti di scena
    private Stage stage;
    private Scene scene;
    private Parent root;
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

    public void loginAction(ActionEvent event) throws Exception{
        if(utenteSalvato.equals(new Utente(textFieldNomeUtente.getText(),textFieldPassword.getText()))){
            // caricamento della nuova scena
            root = FXMLLoader.load(getClass().getResource("/main/application/main-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ProjectManager");
            stage.show();
            // print sul terminale
            System.out.println("OK");
        } else {
            // visualizzazione del popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Login errato");
            alert.setContentText("Nome utente e/o password errste");

            alert.showAndWait();
            // print sul terminale
            System.out.println("LOGIN FAILURE");
        }
    }


}