package main.application.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    PieChart pieChart;

    // dichiarazione del numero di password
    private int passwordCritiche=0;
    private int passwordMoltoCritiche=0;
    private int passwordPocoSicure=0;
    private int passwordSicure=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // analisi delle password
        for(Credenziali c: MainAppController.listaCredenzialiUtente){
            if(Credenziali.singlePasswordCheck(c.getPassword()) == 0){
                passwordMoltoCritiche++;
            } else if(Credenziali.singlePasswordCheck(c.getPassword())<10){
                passwordCritiche++;
            } else if(Credenziali.singlePasswordCheck(c.getPassword())<18) {
                passwordPocoSicure++;
            } else if(Credenziali.singlePasswordCheck(c.getPassword())>=18) {
                passwordSicure++;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Molto Critiche",passwordMoltoCritiche),
                new PieChart.Data("Critiche",passwordCritiche),
                new PieChart.Data("Poco Sicure",passwordPocoSicure),
                new PieChart.Data("Sicure",passwordSicure)
        );

        pieChart.getData().addAll(pieChartData);
    }
}
