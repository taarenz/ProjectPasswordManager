package main.application.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.database.DBHandler;
import main.application.model.Credenziali;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Controller per la gestione dell'aggiunta di una nuova password.
 */
public class NuovaPasswordController {
    // dichiarazione variabili
    Parent root;
    // dichiarazione delle variabili di scena
    @FXML TextField textFieldUrlSito;
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField passwordFieldUno;  // indica i due campi in cui viene inserita la password con conferma password
    @FXML PasswordField passwordFieldDue;
    @FXML Button bottoneConfermaInserimento;
    @FXML Button generaPassword;

//    // initialize per aggiungere listener allo slider
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//    }

    /**
     * Metodo per confermare l'aggiunta della nuova password.
     * Scrittura della nuova password nel database.
     * SQLException gestita dal metodo.
     */
    public void confermaNuovaPassword(){
        // controllo che tutti i campi obbligatori siano stati compilati
        if(fieldCheck()){
            // controllo che i campi password siano uguali
            if(passwordFieldDue.getText().equals(passwordFieldUno.getText())){
                // verifica quale costruttore utilizzare
                Credenziali temp;
                if(textFieldNomeUtente.getText().equals("")){
                    temp = new Credenziali(passwordFieldDue.getText(), textFieldUrlSito.getText(), MainApp.loggedUser);
                } else{
                    temp = new Credenziali(textFieldNomeUtente.getText(), passwordFieldDue.getText(), textFieldUrlSito.getText(), MainApp.loggedUser);
                }

                // controllo che la lista non contenga già la credenziale inserita
                if(!credenzialePresente(temp)){

                    Connection connection = DBHandler.getConnection();
                    try {
                        String query = "INSERT INTO passwords (website, username, password, user_id, created_at)"+"values (?, ?, ?, ?, ?)";
                        PreparedStatement s = connection.prepareStatement(query);
                        s.setString(1, temp.getUrlSitoWeb());
                        s.setString(2, temp.getNomeUtente());
                        s.setString(3, temp.getPassword());
                        s.setInt(4, temp.getUser_id());
                        s.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));

                        int rs = s.executeUpdate();
                        //TODO: remove debug
                        if (rs==1) {
                            System.out.println("Password added");
                        }
                    } catch (SQLException e) {
                        DBHandler.queryException();
                    }

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

                //TODO: remove debug
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

    /**
     * Controlla che la credenziale inserita sia presente o meno all'interno della lista.
     * @param c -> Tipo: <code>Credenziali</code> (credenziale da controllare).
     * @return true se la <code>c</code> e' stata trovata; false altrimenti.
     */
    public boolean credenzialePresente(Credenziali c){
        for(Credenziali credenziali : MainAppController.listaCredenzialiUtente){
            if(c.equals(credenziali)) return true;
        }
        return false;
    }

    /**
     * Metodo che controlla che i campi sitoUrl e password siano stati compilati correttamente.
     * @return true se tutti i campi sono compilati correttamente; false altrimenti.
     */
    private boolean fieldCheck(){
        if(textFieldUrlSito.getText().equals("")){
            return false;
        }
        if(passwordFieldUno.getText().equals("")){
            return false;
        }
        return !passwordFieldDue.getText().equals("");
    }


    /**
     * Metodo che apre il popup per la generazione delle password.
     * @throws IOException quando il popup non si apre.
     */
    public void aperturaPopupGenerazionePassword() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/application/popup-generazione-psw.fxml"));
        root = loader.load();
        Scene newScene = new Scene(root);

        Stage inputStage = new Stage();
        inputStage.setScene(newScene);
        inputStage.setResizable(false);
        inputStage.setTitle("Generazione Password");
        inputStage.initModality(Modality.APPLICATION_MODAL);
        inputStage.showAndWait();

        if(!PopUpGenerazionePSWController.passwordGenerata.equals("")){
            passwordFieldUno.setText(PopUpGenerazionePSWController.passwordGenerata);
            passwordFieldDue.setText(PopUpGenerazionePSWController.passwordGenerata);
        }
        PopUpGenerazionePSWController.passwordGenerata = "";
    }

}
