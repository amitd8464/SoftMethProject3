package org.example.clinicmanagerjavafx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;

import java.io.IOException;

public class HelloApplication<ActionEvent> extends Application {
    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Pane appointmentInfoPane;

    @FXML
    private ChoiceBox<?> chosenProvider;

    @FXML
    private ChoiceBox<?> chosenTimeslot;

    @FXML
    private Pane officeAppointmentSelectorPane;

    @FXML
    private Pane imagingAppointmentSelectorPane;

    @FXML
    private Pane visitTypePane;

    @FXML
    private RadioButton officeRadioButton;

    @FXML
    private RadioButton imagingRadioButton;

    @FXML
    void showOfficeDetailSelector(ActionEvent event) {
        // office radio button is selected, which triggers this function
        // show officeAppointmentSelectorPane
    }

    @FXML
    void showImagingDetailSelector(ActionEvent event){}
    // imaging radio button is selected, which triggers this function
    // show imagingAppointmentSelectorPane

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Clinic Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}