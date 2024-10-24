package org.example.clinicmanagerjavafx;

import clinic.*;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import util.Date;
import util.List;

import java.io.File;
import java.sql.Time;

public class HelloController {

    // GUI Nodes
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab scheduleTab;
    @FXML
    private Pane appointmentInfoPane;
    @FXML
    private Pane officeAppointmentSelectorPane;
    @FXML
    private Pane imagingAppointmentSelectorPane;
    // these need to be refactored into ComboBox as well
    @FXML
    private ChoiceBox<String> chosenTimeslotImaging;
    @FXML
    private ChoiceBox<String> chosenTimeslotOffice;
    @FXML
    private DatePicker appointmentDatePicker;
    // -------------------------------------------------------
    @FXML
    private ComboBox<Provider> cmbChosenProvider;
    @FXML
    private Pane visitTypePane;
    @FXML
    private ToggleGroup buttonToggleGroup;
    @FXML
    private RadioButton officeRadioButton;
    @FXML
    private RadioButton imagingRadioButton;


    // logic variables:
    @FXML
    private boolean providersLoaded;
    @FXML
    private String providersFileName;

    // variables that store user information temporarily, to be uploaded to backend:

        // user info
        private String patientFname;
        private String patientLname;
        private Date patientDOB;

        // universal info
        private Provider apptProvider;
        private Timeslot apptTimeslot;

        // for Schedule appointment
        private Date apptDate;
        private String visitType;

        // for Reschedule appointment
        private Timeslot oldTimeslot;
        private Timeslot newTimeslot;



    @FXML
    void setTimeslotOptions(){
        try{
            Timeslot temp;
            for (int i = 1; i < 13; i++){
                temp = Timeslot.stringToTimeSlot(String.valueOf(i));
                chosenTimeslotOffice.getItems().add("(" + i + ") " + temp);
                chosenTimeslotImaging.getItems().add("(" + i + ") " + temp);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " caught at line 53 in HelloController.java");
        }
    }

    @FXML
    void showOfficeDetailSelector(javafx.event.ActionEvent event) {
        officeRadioButton.setSelected(true);
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);
        imagingRadioButton.setSelected(false);
    }

    @FXML
    void showImagingDetailSelector(javafx.event.ActionEvent event) {
        imagingRadioButton.setSelected(true);
        imagingAppointmentSelectorPane.setVisible(true);
        officeAppointmentSelectorPane.setVisible(false);
        officeRadioButton.setSelected(false);
    }

    // Methods that create nodes:

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
            loadProviders();
            providersLoaded = true;
            setProviderDropdown();
        }
    }

    @FXML
    void loadProviders(){
        new ClinicManager();
        ClinicManager.readProviders(providersFileName);
    }

    // setter methods, called in initialize():

    @FXML
    private void setListeners(){

        // for chosenProvider ComboBox
        cmbChosenProvider.setOnMouseClicked((event) -> {
            if (!providersLoaded) showLoadProviders();
        });

    }

    private void chosenProviderSetConverter(){
        cmbChosenProvider.setConverter(new StringConverter<>() {
            @Override
            public String toString(Provider provider) {
                if (provider instanceof Doctor d){
                    if (d.getProfile().getFname().equals("Placeholder")){
                        return "Choose a provider...";
                    }
                    else if (d.getProfile().getFname().equals("LOAD PROVIDERS")) return "Click to load providers";
                    return d.getProfile().getFname() + " " + d.getProfile().getLname() + " (#" + d.getNPI() + ")";
                }
                return null;
            }
            @Override
            public Provider fromString(String string) { return null; }
        });
    }

    private void setValues(){
        // Associate radio buttons with the ToggleGroup
        officeRadioButton.setToggleGroup(buttonToggleGroup);
        imagingRadioButton.setToggleGroup(buttonToggleGroup);
        officeRadioButton.setSelected(true);

        // Set initial visibility
        officeAppointmentSelectorPane.setVisible(true);
        imagingAppointmentSelectorPane.setVisible(false);

        chosenTimeslotOffice.setValue("Choose a timeslot");
        chosenTimeslotImaging.setValue("Choose a timeslot");

        chosenProviderSetConverter();
        Doctor loadProvidersPlaceholder = new Doctor(new Profile("LOAD PROVIDERS", "", new Date(0, 0, 0)), "BRIDGEWATER", "FAMILY", "");
        cmbChosenProvider.setValue(loadProvidersPlaceholder);

        // set timeslot values in dropdown
        setTimeslotOptions();
        providersLoaded = false;
    }

    // set provider values in dropdown
    private void setProviderDropdown(){
        // create list to store providers. Populate it with providers from getProviders()
        ObservableList<Provider> observableProviders = FXCollections.observableArrayList();
        for (Provider p : ClinicManager.getProviders()){
            if (p instanceof Doctor d) observableProviders.add(d);
        }
        Doctor placeholder = new Doctor(new Profile("Placeholder", "", new Date(0, 0, 0)), "BRIDGEWATER", "FAMILY", "");
        observableProviders.add(placeholder);
        cmbChosenProvider.setItems(observableProviders);
        cmbChosenProvider.setValue(placeholder);
    }

    @FXML
    public void initialize(){
        setListeners();
        setValues();



    }
}
