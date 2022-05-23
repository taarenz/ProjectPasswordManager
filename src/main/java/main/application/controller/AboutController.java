package main.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import main.application.model.Credenziali;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    // dichiarazione delle variabili di scena
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // dichiarazione delle variabili per contare i vari tipi di password
        int pswSicure = 0;
        int pswPocoSicure = 0;
        int pswCritiche = 0;
        int pswMoltoCritiche = 0;

        for(Credenziali c : MainAppController.listaCredenzialiUtente){
            if(Credenziali.singlePasswordCheck(c.getPassword()) == 0){
                pswMoltoCritiche++;
            } else if(Credenziali.singlePasswordCheck(c.getPassword()) < 10 ){
                pswCritiche++;
            } else if (Credenziali.singlePasswordCheck(c.getPassword()) < 18 ){
                pswPocoSicure++;
            } else if(Credenziali.singlePasswordCheck(c.getPassword()) < 20 ){
                pswSicure++;
            }
        }

        System.out.println(pswSicure);
        System.out.println(pswPocoSicure);
        System.out.println(pswCritiche);
        System.out.println(pswMoltoCritiche);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sicure", pswSicure),
                new PieChart.Data("Poco sicure"  , pswPocoSicure),
                new PieChart.Data("Critiche" , pswCritiche),
                new PieChart.Data("Molto critiche" , pswMoltoCritiche)
        );

        PieChart graficoPassword = new PieChart(pieChartData);
    }
}
