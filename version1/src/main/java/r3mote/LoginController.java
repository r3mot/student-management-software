package r3mote;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.chrono.IsoChronology;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import r3mote.Backend.SQLDataBase;
import r3mote.Backend.Security;

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
    private Label loginMessageLabel;

    @FXML
    void addUserOnClick(ActionEvent event) throws Exception {

        if(fieldUserName.getText() != "" && fieldPassword.getText() != ""){
            createUser(fieldUserName.getText(), fieldPassword.getText());
             
        }
    }

    @FXML
    void loginOnClick(ActionEvent event) throws Exception {

        String password = fieldPassword.getText();
        String username = fieldUserName.getText();

        SQLDataBase db = new SQLDataBase();
        boolean isSuccess;

        isSuccess = db.login(username, password);

        if(isSuccess == true){
            SceneController scene = new SceneController();
            scene.switchToHome(event);
        }

        // String toStore = "1234";
        // String encryptedStored = aes.encryptPassword(toStore);

        // String toCheck = "1234";
        // String decryptedStored = aes.decryptPassword(encryptedStored);

        // System.out.println("Initial stored password: " + toStore);
        // System.out.println("Encrypted password: " + encryptedStored);

        // System.out.println();
        // System.out.println("Check against this input: " + toCheck);
        // System.out.println("Decrypted stored password: " + decryptedStored);
    }
}
