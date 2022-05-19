package main.application.model;

import java.util.Objects;

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
}
