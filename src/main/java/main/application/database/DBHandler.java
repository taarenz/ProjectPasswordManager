package main.application.database;

import javafx.scene.control.Alert;
import java.sql.*;


/**
 * Classe per la gestione delle operazioni di connessione al database.
 * Gestisce le eccezioni del database.
 */
public class DBHandler extends Configs {

	static Connection dbConnection;

	/**
	 * Metodo per eseguire la connessione al database.
	 * @return stato della connessione al database.
	 */
	public static Connection getConnection()
	{
		String connectionString = "jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Attenzione: driver per la connessione al database mancante!");
			e.printStackTrace();
		}

		try {
			dbConnection = DriverManager.getConnection(connectionString,Configs.dbUser,Configs.dbPass);
		} catch (SQLException e) {
			queryException();
		}
		return dbConnection;
	}


	/**
	 * Gestione della SQLException.
	 * Apertura di un popup con informazioni sull'errore.
	 */
	public static void queryException(){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore critico");
		alert.setHeaderText("Impossibile instaurare una connessione con il database.");
		alert.setContentText("La modifica dei dati verrà annullata al termine della sessione. Riavviare il programma e riprovare.");

		alert.showAndWait();
	}
}
