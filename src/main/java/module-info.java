module edu.smarthome {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens edu.smarthome to javafx.fxml;
    exports edu.smarthome;
    exports edu.smarthome.controller;
    opens edu.smarthome.controller to javafx.fxml;
}