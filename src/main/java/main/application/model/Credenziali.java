package main.application.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Credenziali model
 */
public class Credenziali{
    // attributi
    private String nomeUtente;
    private String password;
    private String urlSitoWeb;
    private boolean hasNomeUtente;
    private int user_id;

    /**
     * Costruttore senza username.
     * @param password -> Tipo: <code>String</code> (nuova password).
     * @param urlSitoWeb -> Tipo: <code>String</code> (nome/url del sito/app).
     * @param user_id -> Tipo: <code>int</code> (id univoco dell'utente loggato).
     */
    public Credenziali(String password, String urlSitoWeb, int user_id) {
        this.nomeUtente = null;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = false;
        this.user_id = user_id;
    }

    /**
     * Costruttore con username.
     * @param nomeUtente -> Tipo: <code>String</code> (username/email).
     * @param password -> Tipo: <code>String</code> (nuova password).
     * @param urlSitoWeb -> Tipo: <code>String</code> (nome/url del sito/app).
     * @param user_id -> Tipo: <code>int</code> (id univoco dell'utente loggato).
     */
    public Credenziali(String nomeUtente, String password, String urlSitoWeb, int user_id) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = true;
        this.user_id = user_id;
    }

    // metodi getter e setter

    /**
     * Getter: nome utente della credenziale.
     * @return nome utente.
     */
    public String getNomeUtente() {
        return nomeUtente;
    }

    /**
     * Setter: nome utente della credenziale.
     * @param nomeUtente -> Tipo <code>String</code> (username/email).
     */
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    /**
     * Getter: password della credenziale.
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter: password della credenziale.
     * @param password -> Tipo <code>String</code> (password).
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter: sito web della credenziale.
     * @return sito web.
     */
    public String getUrlSitoWeb() {
        return urlSitoWeb;
    }

    /**
     * Setter: sito web della credenziale.
     * @param urlSitoWeb -> Tipo <code>String</code> (nome/url del sito/app).
     */
    public void setUrlSitoWeb(String urlSitoWeb) {
        this.urlSitoWeb = urlSitoWeb;
    }


    /**
     * Getter: id dell'utente "proprietario" della credenziale.
     * @return user id.
     */
    public int getUser_id(){return user_id;}


    /**
     * Metodo equals. Controlla se due credenziali sono identiche.
     * @param c -> Tipo: <code>Credenziali</code> (credenziale da controllare).
     * @return true se le credenziali sono uguali; false altrimenti.
     */
    public boolean equals(Credenziali c) {
        if (this == c) return true;
        if (c == null || getClass() != c.getClass()) return false;
        Credenziali that = (Credenziali) c;
        return Objects.equals(nomeUtente, that.nomeUtente) && Objects.equals(password, that.password) && Objects.equals(urlSitoWeb, that.urlSitoWeb);
    }


    /**
     * Metodo toString. Converte l'oggetto di tipo <code>Credenziali</code> in <code>String</code>.
     * @return credenziale convertita in stringa.
     */
    public String toString(){
        if(this.hasNomeUtente)
            return "Sito: " + this.urlSitoWeb+ " - User: "+this.nomeUtente+" - Password: "+password;
        else
            return "Sito: " + this.urlSitoWeb+" - Password: "+password;
    }


    /**
     * Metodo per controllare l'efficacia di una password.
     * @param toCheck -> Tipo <code>String</code> (password da controllare).
     * @return punteggio ottenuto dalla password controllata.
     */
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

        //TODO: remove?
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
