package main.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaPasswordController implements Initializable {
    Credenziali credenziali = null;
    // dichiarazione oggetti di scena
    @FXML TableView<Credenziali> tableViewCredenziali = new TableView<>();
    @FXML TableColumn colonnaSitoWeb = new TableColumn<Credenziali, String>("Sito Web");
    @FXML TableColumn colonnaNomeUtente = new TableColumn<Credenziali, String>("Nome utente");
    @FXML TableColumn colonnaPassword = new TableColumn<Credenziali, String>("Password");

    // metodo initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewCredenziali.setItems(MainAppController.listaCredenzialiUtente);

        colonnaSitoWeb.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("urlSitoWeb"));
        colonnaNomeUtente.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("nomeUtente"));
        colonnaPassword.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("password"));
    }




}
