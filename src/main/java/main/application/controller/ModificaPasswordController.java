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
    @FXML CheckBox checkBoxConferma;  // obbligatoria se si vuole modificare o eliminare una password
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

            // controllo che le password nuove siano uguali
            if(passwordFieldNuovaPasswordUno.getText().equals(passwordFieldNuovaPasswordDue.getText())){
                // cambio della password
                int indice = getIndiceOggetto(choiceBoxSitoDaModificare.getValue());
                //TODO scrivere la nuova password sul database
                int previousCredentialUserID = MainAppController.listaCredenzialiUtente.get(indice).getUser_id();
                String previousCredentialWebsite = MainAppController.listaCredenzialiUtente.get(indice).getUrlSitoWeb();
                String previousCredentiaUsername = MainAppController.listaCredenzialiUtente.get(indice).getNomeUtente();
                String previousCredentialPassword = MainAppController.listaCredenzialiUtente.get(indice).getPassword();


                MainAppController.listaCredenzialiUtente.get(indice).cambioPassword(passwordFieldNuovaPasswordUno.getText());
                Credenziali temp = MainAppController.listaCredenzialiUtente.get(indice);

                Connection connection = DBHandler.getConnection();

                String tempUsername;
                tempUsername = previousCredentiaUsername;

                try {
                    String query = "UPDATE passwords SET password = ? WHERE user_id = ? AND website = ? AND username = ? AND password = ?";
                    PreparedStatement s = connection.prepareStatement(query);
                    s.setString(1, temp.getPassword());
                    //TODO: password hash
                    s.setInt(2, previousCredentialUserID);
                    s.setString(3, previousCredentialWebsite);
                    s.setString(4, previousCredentiaUsername);
                    s.setString(5, previousCredentialPassword);
                    System.out.println(s);
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
                checkBoxConferma.setSelected(false);
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
            alert.setHeaderText("Campi non corretti");
            alert.setContentText("Per poter modificare la password occorre selezionare il checkbox ed inserire la nuova password");
            alert.showAndWait();
        }

    }

    public void confermaBottoneElimina(){
        // verifica che la choice box non sia vuota
        if(!choiceBoxSitoDaModificare.getSelectionModel().isEmpty() && checkBoxConferma.isSelected()) {
            //richiesta conferma
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sicuro di voler cancellare la password selezionata?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // processo di eliminazione
                Credenziali credenzialiElimate = choiceBoxSitoDaModificare.getValue();
                if(MainAppController.listaCredenzialiUtente.contains(credenzialiElimate)){
                    int indice = getIndiceOggetto(choiceBoxSitoDaModificare.getValue());
                    Credenziali temp = MainAppController.listaCredenzialiUtente.get(indice);

                    Connection connection = DBHandler.getConnection();
                    try {
                        String query = "DELETE FROM passwords WHERE user_id = ? AND website = ? AND username = ? AND password = ?";
                        PreparedStatement s = connection.prepareStatement(query);
                        s.setInt(1, temp.getUser_id());
                        s.setString(2, temp.getUrlSitoWeb());
                        s.setString(3, temp.getNomeUtente());
                        s.setString(4, temp.getPassword());
                        int rs = s.executeUpdate();
                        if (rs==1) {
                            System.out.println("Password deleted");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    MainAppController.listaCredenzialiUtente.remove(credenzialiElimate);

                    checkBoxConferma.setSelected(false);
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
            alert.setContentText("Per poter eliminare le credenziali bisogna obbligatoriamente inserire nella ChoiceBox la credenziale da eliminare e selezionare il check box.");

            alert.showAndWait();
        }

    }

    // metodo per verificare che tutti i campo
    private boolean checkCampi(){
        if(!checkBoxConferma.isSelected()){
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
