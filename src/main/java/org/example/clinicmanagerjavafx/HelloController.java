package org.example.clinicmanagerjavafx;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import clinic.Timeslot;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private boolean providersLoaded;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab scheduleTab;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Pane appointmentInfoPane;

    @FXML
    private ChoiceBox<String> chosenTimeslotOffice;

    @FXML
    private ChoiceBox<String> chosenTimeslotImaging;

    @FXML
    private ChoiceBox<String> chosenProviderOffice;

    @FXML
    private ChoiceBox<String> chosenProviderImaging;

    @FXML
    private Pane officeAppointmentSelectorPane;

    @FXML
    private Pane imagingAppointmentSelectorPane;

    @FXML
    private Pane visitTypePane;

    @FXML
    private ToggleGroup buttonToggleGroup;

    @FXML
    private RadioButton officeRadioButton;

    @FXML
    private RadioButton imagingRadioButton;

    @FXML
    void setTimeslotOptions(){
        try{
            Timeslot temp;
            for (int i = 1; i < 13; i++){
                temp = Timeslot.stringToTimeSlot(String.valueOf(i));
                chosenTimeslotOffice.getItems().add(temp.toString());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " caught at line 53 in HelloController.java");
        }
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

    private void showPopupMessage(Tab tab) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("\nPlease chose a file to load providers from.");
        alert.getButtonTypes().setAll(ButtonType.OK); // This sets the default button to "OK"
        alert.showAndWait();
    }

    @FXML
    private void setListeners(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == scheduleTab && !providersLoaded){
                showPopupMessage(newTab);
            }
        });
    }

    @FXML
    private void setInitialPopup(){}

    @FXML
    public void initialize(){
        setListeners();
        // Ensure the radio buttons are properly associated with the ToggleGroup
        officeRadioButton.setToggleGroup(buttonToggleGroup);
        imagingRadioButton.setToggleGroup(buttonToggleGroup);
        officeRadioButton.setSelected(true);

        // Optionally set initial visibility
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);

        chosenTimeslotOffice.setValue("Choose a timeslot");
        setTimeslotOptions();
    }
}
