package r3mote;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import r3mote.Backend.SQLDataBase;

public class LoginController extends SQLDataBase {

    SceneController scene = new SceneController();

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

        boolean loginSucessfull = false;

        if(fieldUserName.getText() != "" && fieldPassword.getText() != ""){
            loginSucessfull = login(fieldUserName.getText(), fieldPassword.getText());
            if(loginSucessfull == true){
                scene.switchToHome(event);
            }else{
                 loginMessageLabel.setTextFill(Color.RED);
            }
        }

        
    }
}
