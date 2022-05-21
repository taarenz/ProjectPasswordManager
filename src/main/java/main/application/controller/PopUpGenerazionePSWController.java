package main.application.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller per la generazione di una password random.
 */
public class PopUpGenerazionePSWController implements Initializable {
    // dichiarazione attributi
    @FXML AnchorPane anchorPane;
    @FXML Button generaPassword;
    @FXML Slider lunghezzaPassword;
    @FXML CheckBox lettereNumeriCheckbox;
    @FXML CheckBox caratteriSpecialiCheckbox;
    @FXML CheckBox maiuscoleCheckbox;
    @FXML Label labelPassword;
    @FXML Button confermaAggiunta;

    public static String passwordGenerata = "";

    private int passwordLength; // indica la lunghezza della password indicata con lo slider
    private final String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private final String[] symbols = new String[] {"!","#","$","%","&","'","(",")","*","+",",","\"","-",".","/",":",";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~"};

    /**
     * Inizializzazione della scena.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordLength = (int) lunghezzaPassword.getValue();
        lunghezzaPassword.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                passwordLength = (int) lunghezzaPassword.getValue();
            }
        });
    }

    /**
     * Metodo chiamato al click sul bottone per la generazione.
     * Chiama il metodo per la generazione della password e la mostra all'utente.
     */
    public void creazionePassword (){
        passwordGenerata = generatePassword();
        labelPassword.setText(passwordGenerata);
        passwordGenerata = "";
    }


    /**
     * Metodo per confermare la password generata.
     */
    public void confermaPassword(){
        passwordGenerata = labelPassword.getText();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }


    /**
     * Metodo per la generazione effettiva della password.
     * @return <code>String</code> -> password generata in base ai criteri selezionati dall'utente.
     */
    public String generatePassword(){
        String[] array = new String[passwordLength];

        if(!lettereNumeriCheckbox.isSelected()){
            return "Lettere e numeri devono essere selezionati";
        } else if(!caratteriSpecialiCheckbox.isSelected() && !maiuscoleCheckbox.isSelected()){

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

        //TODO remove debug
        System.out.println("Password generata: " + arrayConvert(array));

        return arrayConvert(array);
    }

    //metodi e variabili ausiliari per generatePassword()
    /**
     * Metodo ausiliario per la generazione di un valore random in un range di valori.
     * @param max -> Tipo: <code>int</code> (valore massimo del range da generare).
     * @param min -> Tipo: <code>int</code> (valore minimo del range da generare).
     * @return numero intero generato.
     */
    private int randomNumber(int max, int min){
        int range = max - min + 1;
        return  (int)(Math.random() * range) + min;
    }

    /**
     * Metodo ausiliario per la conversione di un array di stringhe in stringa.
     * @param array -> Tipo <code>String[]</code> (array di String da convertire in String).
     * @return array convertito in stringa.
     */
    private String arrayConvert(String[] array){
        StringBuilder finalPsw = new StringBuilder();
        for (String character: array) {
            finalPsw.append(character);
        }
        return finalPsw.toString();
    }
}
