package org.example.clinicmanagerjavafx;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class HelloController {

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Pane appointmentInfoPane;

    @FXML
    private ChoiceBox<String> chosenProvider;

    @FXML
    private ChoiceBox<String> chosenTimeslot;

    @FXML
    private Pane officeAppointmentSelectorPane;

    @FXML
    private Pane imagingAppointmentSelectorPane;

    @FXML
    private Pane visitTypePane;

    @FXML
    private ToggleGroup buttonToggleGroup;

    // FXML will handle the instantiation
    @FXML
    private RadioButton officeRadioButton;

    @FXML
    private RadioButton imagingRadioButton;

    @FXML
    void setTimeslotOptions(ChoiceBox<?> chosenTimeslot){

        chosenTimeslot.getItems().addAll();
    }

    @FXML
    void showOfficeDetailSelector(javafx.event.ActionEvent event) {
        System.out.println("Office radio button clicked");
        officeRadioButton.setSelected(true);
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);
        imagingRadioButton.setSelected(false);
    }

    @FXML
    void showImagingDetailSelector(javafx.event.ActionEvent event) {
        System.out.println("Imaging radio button clicked");
        imagingRadioButton.setSelected(true);
        imagingAppointmentSelectorPane.setVisible(true);
        officeAppointmentSelectorPane.setVisible(false);
        officeRadioButton.setSelected(false);
    }

    @FXML
    public void initialize() {
        // Ensure the radio buttons are properly associated with the ToggleGroup
        officeRadioButton.setToggleGroup(buttonToggleGroup);
        imagingRadioButton.setToggleGroup(buttonToggleGroup);
        officeRadioButton.setSelected(true);

        // Optionally set initial visibility
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);

        setTimeslotOptions(chosenTimeslot);
    }
}
