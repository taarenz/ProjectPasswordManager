package main.application.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class PopUpGenerazionePSWController implements Initializable {
    // dichiarazione attributi
    @FXML Button generaPassword;
    @FXML Slider lunghezzaPassword;
    @FXML CheckBox lettereNumeriCheckbox;
    @FXML CheckBox caratteriSpecialiCheckbox;
    @FXML CheckBox maiuscoleCheckbox;

    private int passwordLength; // indica la lunghezza della password indicata con lo slider
    private final String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private final String[] symbols = new String[] {"!","#","$","%","&","'","(",")","*","+",",","\"","-",".","/",":",";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~"};

    // metodo initialize
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

    //metodo per generare password
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

        System.out.println("Password generata: "+arrayConvert(array));

        return "";
    }

    //metodi e variabili ausiliari per generatePassword()
    private int randomNumber(int max, int min){
        int range = max - min + 1;
        return  (int)(Math.random() * range) + min;
    }

    private String arrayConvert(String[] array){
        StringBuilder finalPsw = new StringBuilder();
        for (String character: array) {
            finalPsw.append(character);
        }
        return finalPsw.toString();
    }
}
