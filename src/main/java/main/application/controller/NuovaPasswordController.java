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
        passwordLength = (int) lunghezzaPassword.getValue();
        lunghezzaPassword.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                passwordLength = (int) lunghezzaPassword.getValue();
            }
        });

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

    //metodo per generare password
    public String generatePassword(){
        String[] array = new String[passwordLength];
        if(!lettereNumeriCheckbox.isSelected()){
            return "Lettere e numeri devono essere selezionati";
        }else if(!caratteriSpecialiCheckbox.isSelected() && !maiuscoleCheckbox.isSelected()){

            for(int i=0; i<array.length; i++){
                if(randomNumber(1,0)==1){
                    array[i] = alphabet[randomNumber(25,0)];
                }else {
                    array[i] = ""+randomNumber(9, 0);
                }
            }
        }else if(caratteriSpecialiCheckbox.isSelected() && !maiuscoleCheckbox.isSelected()){

            for(int i=0; i<array.length; i++){
                int varType = randomNumber(900,0);
                if(varType>=0 && varType<300){
                    array[i] = alphabet[randomNumber(25,0)];
                }else if(varType>=300 && varType<600){
                    array[i] = ""+randomNumber(9, 0);
                }else{
                    array[i] = symbols[randomNumber(symbols.length-1, 0)];
                }
            }
        }else if (!caratteriSpecialiCheckbox.isSelected() && maiuscoleCheckbox.isSelected()){

            for(int i=0; i<array.length; i++){
                int varType = randomNumber(900,0);
                if(varType>=0 && varType<300){
                    array[i] = alphabet[randomNumber(25,0)];
                }else if(varType>=300 && varType<600){
                    array[i] = ""+randomNumber(9, 0);
                }else{
                    array[i] = alphabet[randomNumber(25,0)].toUpperCase(Locale.ROOT);
                }
            }
        }else if(caratteriSpecialiCheckbox.isSelected() && maiuscoleCheckbox.isSelected()){

            for(int i=0; i<array.length; i++){
                int varType = randomNumber(1200,0);
                if(varType>=0 && varType<300){
                    array[i] = alphabet[randomNumber(25,0)];
                }else if(varType>=300 && varType<600){
                    array[i] = ""+randomNumber(9, 0);
                }else if (varType>=600 && varType<900){
                    array[i] = alphabet[randomNumber(25,0)].toUpperCase(Locale.ROOT);
                }else{
                   array[i] = symbols[randomNumber(symbols.length-1, 0)];
                }
            }
        }

        System.out.println("Password generata: "+arrayConvert(array));

        return "";
    }

    //metodi e variabili ausiliari per generatePassword()
    private final String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private final String[] symbols = new String[] {"!","#","$","%","&","'","(",")","*","+",",","\"","-",".","/",":",";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~"};
    private int randomNumber(int max, int min){
        int range = max - min + 1;
        return  (int)(Math.random() * range) + min;
    }
    private int passwordLength;
    private String arrayConvert(String[] array){
        StringBuilder finalPsw = new StringBuilder();
        for (String character: array) {
            finalPsw.append(character);
        }
        return finalPsw.toString();
    }

}
