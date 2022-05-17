package main.application.model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utente implements Serializable {
    // attributi dell'utente
    private String nomeUtente;  // struttra dati che permette di utilizzare la crypto
    private String password;
    private SecretKey keyCrypto;
    // costruttore
    public Utente(String nomeUtente, String password) {
        // criptazione di ciò che passo come parametro
        try {
            // criptazione nome
            keyCrypto = generateKey("AES");
            Cipher cipher ;
            cipher = Cipher.getInstance("AES");

            this.nomeUtente = encryptString(nomeUtente, keyCrypto, cipher);

            // criptazione password
            this.password = encryptString(password, keyCrypto, cipher);

        } catch (Exception e){
            System.out.println("ERRORE CRYPTO");
        }
    }

    public Utente(Utente u){
        this.nomeUtente = u.nomeUtente;
        this.password = u.password;
        this.keyCrypto = u.keyCrypto;
    }

    // metodi getter
    public String getNomeUtente() {
        return nomeUtente;
    }
    public String getPassword() {
        return password;
    }
    public SecretKey getKeyCrypto() {
        return keyCrypto;
    }

    // metodi di criptazione
    public SecretKey generateKey (String tipoCriptazione){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance(tipoCriptazione);
            return keyGenerator.generateKey();
        } catch (Exception e){
            return null;
        }
    }

    public String encryptString(String dato, SecretKey myKey, Cipher cipher){
        try{
            cipher.init(Cipher.ENCRYPT_MODE, myKey);
            byte[] datoCriptato = cipher.doFinal(dato.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(datoCriptato);
        } catch (Exception e){
            return null;
        }
    }

    public String decryptString(byte[] datoCriptato, SecretKey myKey, Cipher cipher){
        try{
            cipher.init(Cipher.DECRYPT_MODE, myKey);
            byte[] datoDecriptato = cipher.doFinal(Base64.getDecoder().decode(datoCriptato));

            return new String(datoDecriptato);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    // metodo equals
    public boolean equals(Utente u2) {
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES");

            String nomeU2 = decryptString(u2.nomeUtente.getBytes(), u2.getKeyCrypto(), cipher);
            String passwordU2 = decryptString(u2.password.getBytes(), u2.getKeyCrypto(), cipher);
            String nomeThis = decryptString(this.nomeUtente.getBytes(), keyCrypto, cipher);
            String passwordThis = decryptString(this.password.getBytes(), keyCrypto, cipher);

            return nomeThis.equals(nomeU2) && passwordThis.equals(passwordU2);

        } catch (Exception e) {
            return false;
        }
    }
}
