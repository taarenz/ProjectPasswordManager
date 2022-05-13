package main.application.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaPasswordController implements Initializable {
    Credenziali credenziali = null;
    // dichiarazione oggetti di scena
    @FXML TableView<Credenziali> tableViewCredenziali = new TableView<>();
    @FXML TableColumn colonnaSitoWeb = new TableColumn<Credenziali, String>("");
    @FXML TableColumn colonnaNomeUtente = new TableColumn<Credenziali, String>("");
    @FXML TableColumn colonnaPassword = new TableColumn<Credenziali, String>("");

    @FXML TextField searchBar;
    @FXML HBox containerSearchBar;

    // metodo initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewCredenziali.setItems(MainAppController.listaCredenzialiUtente);

        colonnaSitoWeb.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("urlSitoWeb"));
        colonnaNomeUtente.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("nomeUtente"));
        colonnaPassword.setCellValueFactory(new PropertyValueFactory<Credenziali, String>("password"));

        // filtered list per implementare la filtered search
        FilteredList<Credenziali> listaFiltrataProdottiMagazzino = new FilteredList<>(MainAppController.listaCredenzialiUtente, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrataProdottiMagazzino.setPredicate(credenziali -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (credenziali.getUrlSitoWeb().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;

                } else {
                    return false;

                }

            });
        });

        SortedList<Credenziali> listaOrdinata = new SortedList<>(listaFiltrataProdottiMagazzino);

        listaOrdinata.comparatorProperty().bind(tableViewCredenziali.comparatorProperty());

        tableViewCredenziali.setItems(listaOrdinata);
    }
}
