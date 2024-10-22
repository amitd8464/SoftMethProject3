package org.example.clinicmanagerjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class HelloController {

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
    void showOfficeDetailSelector(javafx.event.ActionEvent event) {
        // office radio button is selected, which triggers this function
        // show officeAppointmentSelectorPane
        System.out.println("Office radio button clicked");
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);
    }

    @FXML
    void showImagingDetailSelector(javafx.event.ActionEvent event) {
        // imaging radio button is selected, which triggers this function
        // show imagingAppointmentSelectorPane
        System.out.println("Imaging radio button clicked");
        imagingAppointmentSelectorPane.setVisible(true);
        officeAppointmentSelectorPane.setVisible(false);
    }
}