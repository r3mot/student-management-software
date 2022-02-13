module r3mote {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens r3mote to javafx.fxml;
    exports r3mote;
}
