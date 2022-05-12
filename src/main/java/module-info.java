module main.projectpasswordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens main.application to javafx.fxml;
    exports main.application;
    exports main.application.controller;
    opens main.application.controller to javafx.fxml;
    exports main.application.model;
    opens main.application.model to javafx.fxml;
}