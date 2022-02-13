package r3mote;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SceneController {

    @FXML
    public void switchToHome(ActionEvent event) throws IOException{
        App.setRoot("homescene");
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException{
        App.setRoot("loginscene");
    }
    
}
