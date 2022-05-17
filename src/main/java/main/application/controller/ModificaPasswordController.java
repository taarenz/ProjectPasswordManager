package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.database.DBHandler;
import main.application.model.Credenziali;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModificaPasswordController implements Initializable {
    // dichiarazione degli oggetti di scena
    @FXML Parent root;
    @FXML ChoiceBox<Credenziali> choiceBoxSitoDaModificare;
    @FXML PasswordField passwordFieldVecchiaPassword;
    @FXML PasswordField passwordFieldNuovaPasswordUno;
    @FXML PasswordField passwordFieldNuovaPasswordDue;
    @FXML Button bottoneModifica;
    @FXML Button bottoneElimina;

    // variabile statica per la password generata casualmente

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
                    //TODO scrivere la nuova password sul database
                    Credenziali previousPassword = MainAppController.listaCredenzialiUtente.get(indice);
                    MainAppController.listaCredenzialiUtente.get(indice).cambioPassword(passwordFieldNuovaPasswordUno.getText());
                    Credenziali temp = MainAppController.listaCredenzialiUtente.get(indice);
                    Connection connection = DBHandler.getConnection();
                    String tempUsername;
                    if(previousPassword.getNomeUtente()==null)
                        tempUsername = null;
                    else
                        tempUsername = previousPassword.getNomeUtente();

                    try {
                        String query = "UPDATE passwords SET password = ? WHERE user_id = ? AND website = ? AND username = ? AND password = ?";
                        PreparedStatement s = connection.prepareStatement(query);
                        s.setString(1, temp.getPassword());
                        //TODO: password hash
                        s.setInt(2, previousPassword.getUser_id());
                        s.setString(3, previousPassword.getUrlSitoWeb());
                        s.setString(4, tempUsername);
                        s.setString(5, previousPassword.getPassword());

                        int rs = s.executeUpdate();
                        if (rs==1) {
                            System.out.println("Password updated");
                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

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

                    passwordFieldVecchiaPassword.clear();
                    passwordFieldNuovaPasswordUno.clear();
                    passwordFieldNuovaPasswordDue.clear();
                    choiceBoxSitoDaModificare.getSelectionModel().clearSelection();
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

    // metodo per aprire il popup
    public void aperturaPopupGenerazionePassword() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/application/popup-generazione-psw.fxml"));
        root = loader.load();
        Scene newScene = new Scene(root);

        Stage inputStage = new Stage();
        inputStage.setScene(newScene);
        inputStage.setTitle("Generazione Password");
        inputStage.setResizable(false);
        inputStage.initModality(Modality.APPLICATION_MODAL);
        inputStage.showAndWait();

        if(!PopUpGenerazionePSWController.passwordGenerata.equals("")){
            passwordFieldNuovaPasswordUno.setText(PopUpGenerazionePSWController.passwordGenerata);
            passwordFieldNuovaPasswordDue.setText(PopUpGenerazionePSWController.passwordGenerata);
        }

        PopUpGenerazionePSWController.passwordGenerata = "";
    }
}
