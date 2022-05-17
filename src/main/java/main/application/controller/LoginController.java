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
import main.application.model.Utente;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @FXML TextField textFieldNomeUtente;
    @FXML PasswordField textFieldPassword;
    @FXML Button loginButton;
    @FXML Button registratiButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // caricamento password
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/txtFile/utente_criptato.txt");
            ObjectInputStream os = new ObjectInputStream(fis);

            utenteSalvato = new Utente((Utente) os.readObject());

            os.close();

            System.out.println("OKKKK");

        } catch (Exception e){
            System.out.println("ERRORE FILE");
        }
    }

    public void loginAction(ActionEvent event) throws Exception{

        if(utenteSalvato.equals(new Utente(textFieldNomeUtente.getText(),textFieldPassword.getText()))){
            // connessione con il db
            Connection connection = DBHandler.getConnection();
//          System.out.print(connection);

            try {

                String query = "SELECT * FROM users WHERE username = ?";
                PreparedStatement s = connection.prepareStatement(query);
                s.setString(1, textFieldNomeUtente.getText());

                ResultSet rs = s.executeQuery();
                while (rs.next()){
                    //TODO: check password hash
                    System.out.println(rs.getString("username"));
                    MainApp.loggedUser = rs.getInt("id");
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // caricamento della nuova scena
            root = FXMLLoader.load(getClass().getResource("/main/application/main-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("LOGIN");
            stage.show();
            // print sul terminale
            System.out.println("OK");
        } else {
            // visualizzazione del popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Login errato");
            alert.setContentText("Nome utente e/o password errste");

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


    //Hashing functions (?)
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        /* MessageDigest instance for hashing using SHA256 */
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}

// try
//         {
//         String string1 = "myPassword";
//         System.out.println("\n" + string1 + " : " + toHexString(getSHA(string1)));
//
//         String string2 = "hashtrial";
//         System.out.println("\n" + string2 + " : " + toHexString(getSHA(string2)));
//         }
//         catch (NoSuchAlgorithmException e)
//         {
//         System.out.println("Exception thrown for incorrect algorithm: " + e);
//         }
//         }