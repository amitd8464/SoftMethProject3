package org.example.clinicmanagerjavafx;

import clinic.Provider;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import clinic.Timeslot;
import clinic.ClinicManager;

import java.io.File;

public class HelloController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private boolean providersLoaded;

    @FXML
    private String providersFileName;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab scheduleTab;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Pane appointmentInfoPane;

    @FXML
    private ChoiceBox<String> chosenTimeslotImaging;

    @FXML
    private ChoiceBox<String> chosenTimeslotOffice;

    @FXML
    private ChoiceBox<String> chosenProvider;

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
                chosenTimeslotImaging.getItems().add(temp.toString());
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

    private void createFileChooser(Dialog<String> dialog, DialogPane dialogPane, TextField filePathField, ButtonType okButtonType){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Provider's File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));

        // Show the file chooser dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(dialog.getOwner());
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getName());
            providersFileName = selectedFile.getAbsolutePath();
            dialogPane.lookupButton(okButtonType).setDisable(false);
        }
    }

    private VBox createNodesContainer(TextField filePathField, Button openFileButton){
        VBox nodesContainer = new VBox(filePathField, openFileButton);
        nodesContainer.setStyle("-fx-padding: 10;");
        nodesContainer.setPrefWidth(80);
        nodesContainer.setPrefHeight(50);

        return nodesContainer;
    }

    private DialogPane createDialogPane(){
        DialogPane dialogPane = new DialogPane();
        dialogPane.setPrefHeight(100);
        dialogPane.setPrefWidth(250);
        dialogPane.setContentText("Choose Provider's File..");

        return dialogPane;
    }

    private TextField createFilePathField(){
        TextField filePathField = new TextField();
        filePathField.setText("No file selected.");
        filePathField.setEditable(false); // Make it read-only

        return filePathField;
    }

    private Dialog<String> createLoadFileDialog(DialogPane dialogPane, ButtonType okButtonType){
        Dialog<String> dialog = new Dialog<>();

        // Setup dialog to block background interaction, takes priority
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Load file");

        dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialogPane.lookupButton(okButtonType).setDisable(true);

        return dialog;
    }

    private String dialogShowAndWait(Dialog<String> dialog){
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // If OK is pressed, return a result indicating it
                return "OK";
            }
            // Return null if CANCEL or other buttons are pressed
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    void showLoadProviders() {;
        DialogPane dialogPane = createDialogPane();
        TextField filePathField = createFilePathField();

        // create OK buttonType (for easy search later)
        // ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType okButtonType = ButtonType.OK;

        // Create new Dialog (window to upload providers file)
        Dialog<String> dialog = createLoadFileDialog(dialogPane, okButtonType);

        // Create button to open file
        Button openFileButton = new Button("Choose File...");
        HBox buttonContainer = new HBox(openFileButton);
        buttonContainer.setPrefHeight(10);
        buttonContainer.setPrefWidth(30);
        buttonContainer.setStyle("-fx-alignment: BOTTOM_LEFT;" + "-fx-margin-top: 20;");

        // Functionality for open file button (open a FileChooser dialog)
        openFileButton.setOnAction(e -> createFileChooser(dialog, dialogPane, filePathField, okButtonType));

        // Add nodes to dialog pane
        dialogPane.setContent(createNodesContainer(filePathField, openFileButton));

        // add dialog pane to dialog
        dialog.setDialogPane(dialogPane);
        dialog.setResizable(false);

        // blur background when dialog is showing
        mainAnchorPane.setEffect(new GaussianBlur(5));  // Blur effect on background

        // If result is "OK", then we can load providers using chosen file. If result is "CANCEL", do not load providers.
        String result = dialogShowAndWait(dialog);
        mainAnchorPane.setEffect(null);

        // Call loadProviders only if OK was pressed
        if ("OK".equals(result)) {
            System.out.println("result is OK");
            loadProviders();
        }
    }

    @FXML
    void loadProviders(){
        new ClinicManager();
        ClinicManager.readProviders(providersFileName);
    }

    @FXML
    private void setListeners(){
        chosenProvider.setOnMouseClicked((event) -> {
            if (!providersLoaded) showLoadProviders();
        });
    }

    private void setValues(){
        chosenTimeslotOffice.setValue("Choose a timeslot");
        chosenTimeslotImaging.setValue("Choose a timeslot");
        providersLoaded = false;
    }

    @FXML
    public void initialize(){
        setListeners();
        setTimeslotOptions();
        setValues();
        // Ensure the radio buttons are properly associated with the ToggleGroup
        officeRadioButton.setToggleGroup(buttonToggleGroup);
        imagingRadioButton.setToggleGroup(buttonToggleGroup);
        officeRadioButton.setSelected(true);

        // Optionally set initial visibility
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);
    }
}
