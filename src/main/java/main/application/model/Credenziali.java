package main.application.model;

import java.util.Objects;

public class Credenziali{
    // attributi
    private String nomeUtente;
    private String password;
    private String urlSitoWeb;
    private boolean hasNomeUtente;

    // costruttore
    public Credenziali(String password, String urlSitoWeb) {
        this.nomeUtente = null;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = false;
    }

    public Credenziali(String nomeUtente, String password, String urlSitoWeb) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.urlSitoWeb = urlSitoWeb;
        this.hasNomeUtente = true;
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
            return this.urlSitoWeb+" - ("+this.nomeUtente+") "+password;
        else
            return this.urlSitoWeb + " " + password;
    }
}
