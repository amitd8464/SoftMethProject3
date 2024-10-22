module org.example.clinicmanagerjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.clinicmanagerjavafx to javafx.fxml;
    exports org.example.clinicmanagerjavafx;
}