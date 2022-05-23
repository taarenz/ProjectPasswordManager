package main.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    /**
     * Utente loggato durante l'esecuzione.
     * Variabile globale modificata al login e al logout dell'utente.
     * 0 -> Nessun utente loggato.
     * !=0 -> Utente loggato. Il valore corrisponde alla colonna <code>id</code> nella tabella <code>users</code> del database.
     */
    public static int loggedUser=0;

    /**
     * @param stage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException quando la scena non carica.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 390, 341);
        stage.setResizable(false);
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo main -> chiama metodo launch().
     * @param args ...
     */
    public static void main(String[] args) {
        launch();
    }
}
