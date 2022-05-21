package main.application.model;

/**
 * Utente model
 */
public class Utente{
    // attributi dell'utente
    private String nomeUtente;  // struttra dati che permette di utilizzare la crypto
    private String password;

    /**
     * Costruttore.
     * @param nomeUtente -> Tipo <code>String</code> (username/email).
     * @param password -> Tipo <code>String</code> (password).
     */
    public Utente(String nomeUtente, String password) {
        this.nomeUtente = nomeUtente;
        this.password = password;
    }

    /**
     * Getter: nome utente dell'utente.
     * @return nome utente.
     */
    public String getNomeUtente() {
        return nomeUtente;
    }

    /**
     * Getter: password dell'utente.
     * @return password.
     */
    public String getPassword() {
        return password;
    }
}
