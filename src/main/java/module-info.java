module com.example.client {
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    exports com.example.client.dto;
    opens com.example.client to javafx.fxml;
    exports com.example.client;
}