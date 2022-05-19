package main.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.application.MainApp;
import main.application.database.DBHandler;
import main.application.model.Credenziali;
import main.application.model.Utente;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    // dichiarazione oggetti di scena
    private Stage stage;
    private Scene scene;
    private Parent root;
    // dichiarazione variabili locali
    public Utente utenteSalvato;

    // dichiarazione dei componenti di Scena
    @FXML
    TextField textFieldNomeUtente;
    @FXML
    PasswordField textFieldPassword;
    @FXML
    Button loginButton;
    @FXML
    Button registratiButton;

    public LoginController() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loginAction(ActionEvent event) throws Exception {

        //TODO eliminare controllo su file in favore del DB
        //connessione con il db
        Connection connection = DBHandler.getConnection();
        boolean loginSuccess = false;
//          System.out.print(connection);
        Utente u = new Utente(textFieldNomeUtente.getText(), textFieldPassword.getText());
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement s = connection.prepareStatement(query);
            s.setString(1,u.getNomeUtente());

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                if(rs.getString("password").equals(u.getPassword())){
                    loginSuccess = true;
                    //TODO: check password hash
                    MainApp.loggedUser = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(loginSuccess) {
            // caricamento della nuova scena
            root = FXMLLoader.load(getClass().getResource("/main/application/main-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //Get all user passwords from db
            try {
                String query = "SELECT * FROM passwords WHERE user_id = ?";
                PreparedStatement s = connection.prepareStatement(query);
                s.setInt(1, MainApp.loggedUser);
                Credenziali temp;
                ResultSet rs = s.executeQuery();
                while (rs.next()) {
                    String hasUsername = rs.getString("username");
                    if (rs.wasNull()) {
                        temp = new Credenziali(rs.getString("password"), rs.getString("website"), MainApp.loggedUser);
                    }else{
                        temp = new Credenziali(rs.getString("username"), rs.getString("password"), rs.getString("website"), MainApp.loggedUser);
                    }
                    MainAppController.listaCredenzialiUtente.add(temp);
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("PASS&GER");
            stage.show();
            // print sul terminale
            System.out.println("OK");
        } else {
            // visualizzazione del popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Login errato");
            alert.setContentText("Nome utente e/o password errate");
            alert.showAndWait();
            // print sul terminale
            System.out.println("LOGIN FAILURE");
        }
    }

    public void switchregistratiView() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/application/register-view.fxml"));
        root = loader.load();
        Scene newScene = new Scene(root);

        Stage inputStage = new Stage();
        inputStage.setResizable(false);
        inputStage.setScene(newScene);
        inputStage.setTitle("Registrazione");
        inputStage.initModality(Modality.APPLICATION_MODAL);
        inputStage.showAndWait();
    }


    private static String get_SHA_256_SecurePassword(String passwordToHash,
                                                     String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    String passwordToHash = "password";
    String salt = getSalt();

    String securePassword = get_SHA_256_SecurePassword(passwordToHash, salt);
//    System.out.print(securePassword);

    private static String getSalt()
            throws NoSuchAlgorithmException, NoSuchProviderException
    {
        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return salt.toString();
    }

}