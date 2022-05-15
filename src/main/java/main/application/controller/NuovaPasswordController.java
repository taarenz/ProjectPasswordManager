package main.application.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class NuovaPasswordController implements Initializable {
    // dichiarazione delle variabili di scena
    @FXML TextField textFieldUrlSito;
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField passwordFieldUno;  // indica i due campi in cui viene inserita la password con conferma password
    @FXML PasswordField passwordFieldDue;
    @FXML Button bottoneConfermaInserimento;
    @FXML Button generaPassword;
    @FXML Slider lunghezzaPassword;
    @FXML CheckBox lettereNumeriCheckbox;
    @FXML CheckBox caratteriSpecialiCheckbox;
    @FXML CheckBox maiuscoleCheckbox;

    // initialize per aggiungere listener allo slider
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // metodo per confermare l'aggiunta della nuova password
    public void confermaNuovaPassword(){
        // controllo che tutti i campi obbligatori siano stati compilati
        if(fieldCheck()){
            // controllo che i campi password siano uguali
            if(passwordFieldDue.getText().equals(passwordFieldUno.getText())){
                // verifica quale costruttore utilizzare
                Credenziali temp;
                if(textFieldNomeUtente.getText().equals("")){
                    temp = new Credenziali(passwordFieldDue.getText(), textFieldUrlSito.getText());
                } else{
                    temp = new Credenziali(textFieldNomeUtente.getText(), passwordFieldDue.getText(), textFieldUrlSito.getText());
                }

                // controllo che la lista non contenga già la credenziale inserita
                if(!credenzialePresente(temp)){
                    // password non già inserita
                    MainAppController.listaCredenzialiUtente.add(temp);

                    // pulizia dei campi
                    textFieldNomeUtente.clear();
                    textFieldUrlSito.clear();
                    passwordFieldUno.clear();
                    passwordFieldDue.clear();
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Credenziale già inserita");
                    alert.setContentText("Per poter aggiungere una password bisogna inseire nuovi dati.");

                    alert.showAndWait();
                }

                for(Credenziali c : MainAppController.listaCredenzialiUtente){
                    System.out.println(c);
                }
            } else{
                // comparsa popup
                // visualizzazione del popup
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Password errate");
                alert.setContentText("Le password non corrispondono");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non compilati");
            alert.setContentText("Per poter aggiungere una password bisogna compilare obbligatoriamene i campi sito web e password");

            alert.showAndWait();
        }
    }

    // metodo per verificare che una variabile sia presente o meno all'interno della lista
    public boolean credenzialePresente(Credenziali c){
        for(Credenziali credenziali : MainAppController.listaCredenzialiUtente){
            if(c.equals(credenziali)) return true;
        }

        return false;
    }

    // metodo che controlla che i campi sitoUrl e password siano stati compilati correttamente
    private boolean fieldCheck(){
        if(textFieldUrlSito.getText().equals("")){
            return false;
        }
        if(passwordFieldUno.getText().equals("")){
            return false;
        }
        if(passwordFieldDue.getText().equals("")){
            return false;
        }

        return true;
    }

}
