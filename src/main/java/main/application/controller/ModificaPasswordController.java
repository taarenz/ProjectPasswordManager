package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.application.MainApp;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificaPasswordController implements Initializable {
    // dichiarazione degli oggetti di scena
    @FXML ChoiceBox<Credenziali> choiceBoxSitoDaModificare;
    @FXML PasswordField passwordFieldVecchiaPassword;
    @FXML PasswordField passwordFieldNuovaPasswordUno;
    @FXML PasswordField passwordFieldNuovaPasswordDue;
    @FXML Button bottoneModifica;
    @FXML Button bottoneElimina;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxSitoDaModificare.setItems(MainAppController.listaCredenzialiUtente);
    }

    // conferma bottone modifica
    public void confermaBottoneModifica(){
        // verifica che tutti i campi siano stati usati
        if(checkCampi()){
            // controllo che le password siano corrette
            // psw vecchia
            if(passwordFieldVecchiaPassword.getText().equals(choiceBoxSitoDaModificare.getValue().getPassword())){
                // controllo che le password nuove siano uguali
                if(passwordFieldNuovaPasswordUno.getText().equals(passwordFieldNuovaPasswordDue.getText())){
                    // cambio della password
                    int indice = getIndiceOggetto(choiceBoxSitoDaModificare.getValue());
                    MainAppController.listaCredenzialiUtente.get(indice).cambioPassword(passwordFieldNuovaPasswordUno.getText());

                    choiceBoxSitoDaModificare.setItems(MainAppController.listaCredenzialiUtente);

                    // clear delle caselle di testo
                    passwordFieldVecchiaPassword.clear();
                    passwordFieldNuovaPasswordUno.clear();
                    passwordFieldNuovaPasswordDue.clear();
                    choiceBoxSitoDaModificare.getSelectionModel().clearSelection();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Password errate");
                    alert.setContentText("Le password non corrispondono");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Password errata");
                alert.setContentText("La password non corrisponde con quella precedente");

                alert.showAndWait();
            }
        } else{
            // visualizzazione del popup di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non completati");
            alert.setContentText("Tutti i campi devono essere completati");

            alert.showAndWait();
        }

    }

    public void confermaBottoneElimina(){
        // verifica che la choice box non sia vuota
        if(!choiceBoxSitoDaModificare.getSelectionModel().isEmpty()) {
            //richiesta conferma
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sicuro di voler cancellare la password selezionata?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // processo di eliminazione
                Credenziali credenzialiElimate = choiceBoxSitoDaModificare.getValue();
                if(MainAppController.listaCredenzialiUtente.contains(credenzialiElimate)){
                    MainAppController.listaCredenzialiUtente.remove(credenzialiElimate);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Impossibile trovare le credenziali selezionate");

                    alert.showAndWait();
                }
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non compilati");
            alert.setContentText("Per poter eliminare le credenziali bisogna obbligatoriamente inserire nella ChoiceBox la credenziale da eliminare.");

            alert.showAndWait();
        }

    }

    // metodo per verificare che tutti i campo
    private boolean checkCampi(){
        if(passwordFieldVecchiaPassword.getText().equals(null)){
            return false;
        }
        if(passwordFieldNuovaPasswordUno.getText().equals(null)){
            return false;
        }
        if(passwordFieldNuovaPasswordDue.getText().equals(null)){
            return false;
        }
        if(choiceBoxSitoDaModificare.getSelectionModel().isEmpty()){
            return false;
        }

        return true;
    }

    // metodo per prendere l'indice dell'oggetto
    private int getIndiceOggetto(Credenziali temp){
        for(int i=0; i<MainAppController.listaCredenzialiUtente.size(); i++){
            if(MainAppController.listaCredenzialiUtente.get(i).equals(temp)){
                return i;
            }
        }

        return -1;
    }
}
