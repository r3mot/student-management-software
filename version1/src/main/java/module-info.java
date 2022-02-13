module r3mote {
    requires javafx.controls;
    requires javafx.fxml;

    opens r3mote to javafx.fxml;
    exports r3mote;
}
