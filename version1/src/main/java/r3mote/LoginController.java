package r3mote;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r3mote.Backend.SQLDataBase;

public class LoginController extends SQLDataBase {

    @FXML
    private Button addUserBtn;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldUserName;

    @FXML
    private Button loginBtn;

    @FXML
    void addUserOnClick(ActionEvent event) throws Exception {

        createUser(fieldUserName.getText(), fieldPassword.getText());
    }

    @FXML
    void loginOnClick(ActionEvent event) {

    }

}
