module org.example.clinicmanagerjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.clinicmanagerjavafx to javafx.fxml;
    exports org.example.clinicmanagerjavafx;
}