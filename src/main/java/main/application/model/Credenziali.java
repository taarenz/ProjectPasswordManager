package main.application.model;

import main.application.controller.MainAppController;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Credenziali{
    // attributi
    private String nomeUtente;
    private String password;
    private String urlSitoWeb;
    private boolean hasNomeUtente;
    private int user_id;

    private int id;

    // costruttore
    public Credenziali(String password, String urlSitoWeb, int user_id) {
        this.nomeUtente = null;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = false;
        this.user_id = user_id;
    }

    public Credenziali(String nomeUtente, String password, String urlSitoWeb, int user_id) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = true;
        this.user_id = user_id;
    }

    // metodi getter e setter
    public String getNomeUtente() {
        return nomeUtente;
    }
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUrlSitoWeb() {
        return urlSitoWeb;
    }
    public void setUrlSitoWeb(String urlSitoWeb) {
        this.urlSitoWeb = urlSitoWeb;
    }

    public int getUser_id(){return user_id;}

    // metodo per modificare la password
    public void cambioPassword(String password){
        this.password = password;
    }

    // metodo equals
    public boolean equals(Credenziali o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credenziali that = (Credenziali) o;
        return Objects.equals(nomeUtente, that.nomeUtente) && Objects.equals(password, that.password) && Objects.equals(urlSitoWeb, that.urlSitoWeb);
    }

    // metodo toString
    public String toString(){
        if(this.hasNomeUtente)
            return "Sito: " + this.urlSitoWeb+ " - User: "+this.nomeUtente+" - Password: "+password;
        else
            return "Sito: " + this.urlSitoWeb+" - Password: "+password;
    }

    //Password security check
    public static int singlePasswordCheck(String toCheck){
        int tempScore = toCheck.length();

        Pattern pattern;
        Matcher matcher;

        boolean allNumbers = false;
        for (int i = 0; i < toCheck.length(); i++) {
            if (Character.isDigit(toCheck.charAt(i))) {
                allNumbers = true;
            }
            else {
                allNumbers = false;
                break;
            }
        }

        if(allNumbers){
            return 0;
        }

        pattern = Pattern.compile("[" + "-/@#!*$%^&.'_+={}()"+ "]+");
        matcher = pattern.matcher(toCheck);
        if(matcher.find()){
            return tempScore++;
        }

        pattern = Pattern.compile("[A-Z]");
        matcher = pattern.matcher(toCheck);
        if(matcher.find()){
            tempScore+=2;
        }

        pattern = Pattern.compile("[0-9]");
        matcher = pattern.matcher(toCheck);
        if(matcher.find()){
            tempScore+=3;
        }

        pattern = Pattern.compile("[^a-zA-Z0-9]");
        matcher = pattern.matcher(toCheck);
        if(matcher.find()){
            tempScore+=4;
        }

        boolean repeated = false;

        for (int i = 0; i < toCheck.length(); i++) {

            for (int j = 0; j < toCheck.length(); j++) {

                if (toCheck.charAt(i) == toCheck.charAt(j)) {
                    repeated = true;
                }
            }
        }

        if(repeated){
            tempScore-=2;
        }

//        boolean samePassword = false;
//
//        for (Credenziali credenziale:
//             MainAppController.listaCredenzialiUtente) {
//            if (credenziale.getPassword().equals(toCheck)) {
//                samePassword = true;
//                break;
//            }
//        }
//
//        if (samePassword){
//            tempScore-=5;
//        }

        return tempScore;
    }
}
