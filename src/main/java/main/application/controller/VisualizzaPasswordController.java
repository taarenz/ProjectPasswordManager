package main.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizzaPasswordController implements Initializable {
    Credenziali credenziali = null;
    // dichiarazione oggetti di scena
    @FXML TableView<Credenziali> tableViewCredenziali = new TableView<>();
    @FXML TableColumn colonnaSitoWeb = new TableColumn<Credenziali, String>("");
    @FXML TableColumn colonnaNomeUtente = new TableColumn<Credenziali, String>("");
    @FXML TableColumn colonnaPassword = new TableColumn<Credenziali, String>("");

    @FXML TextField searchBar;
    @FXML Button bottoneCancellaSearchBar;
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

        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                tableViewCredenziali.setItems(filterList(MainAppController.listaCredenzialiUtente, newValue))
        );
    }

    // metodo per vedere se la search bar contiene i match
    private boolean searchFindsMatch(Credenziali credenziali, String searchText){
        return (credenziali.getUrlSitoWeb().toLowerCase().contains(searchText.toLowerCase()));
    }

    private ObservableList<Credenziali> filterList(List<Credenziali> list, String searchText){
        List<Credenziali> filteredList = new ArrayList<>();
        for (Credenziali credenziali1 : list){
            if(searchFindsMatch(credenziali1, searchText)){
                filteredList.add(credenziali1);
                containerSearchBar.setStyle("");
            }
        }

        if(filteredList.size() == 0){
            containerSearchBar.setStyle("-fx-background-radius: 20px; -fx-background-color: #ff6767");
        }

        return FXCollections.observableList(filteredList);
    }

    public void clearSearchBar(){
        searchBar.clear();
    }
}
