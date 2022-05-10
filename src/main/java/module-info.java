module main.projectpasswordmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.application to javafx.fxml;
    exports main.application;
    exports main.application.controller;
    opens main.application.controller to javafx.fxml;
}