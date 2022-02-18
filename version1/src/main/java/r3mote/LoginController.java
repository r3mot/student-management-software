package r3mote;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import r3mote.Backend.SQLDataBase;

public class LoginController extends SQLDataBase{

    SceneController scene = new SceneController();

    @FXML
    private Button addUserBtn, loginBtn;


    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldUserName;

    @FXML
    private Label loginMessageLabel, loginMessageLabel1;

    @FXML
    private Hyperlink forgotPasswordLink; 

    @FXML
    private Pane student_anchor, student_pane, teacher_anchor, teacher_pane;


    @FXML
    void guestLoginOnClick(ActionEvent event) throws IOException {
        scene.switchToHome(event);
    }

    @FXML
    void forgotPasswordOnClick(ActionEvent event) {

    }
    
    @FXML
    void addUserOnClick(ActionEvent event) throws Exception {

        boolean userAdded = false;

        if(fieldUserName.getText() != "" && fieldPassword.getText() != ""){
            userAdded =  addUser(fieldUserName.getText(), fieldPassword.getText());

            if(userAdded == true){
                loginMessageLabel.setTextFill(Color.BLUE);
                loginMessageLabel.setText("User added successfully");

            }    
        }
    }

    @FXML
    void loginOnClick(ActionEvent event) throws Exception {

        String isEmpty = "";
        //boolean loginSucessfull = false;


        if(fieldUserName.getText() == isEmpty && fieldPassword.getText() == isEmpty){

            loginMessageLabel.setTextFill(Color.RED);
            loginMessageLabel.setText("Please enter a username and password.");

        }else{
            if(validateLogin(fieldUserName.getText(), fieldPassword.getText())){
                loginMessageLabel.setTextFill(Color.BLUE);
                loginMessageLabel.setText("Login success!");
                scene.switchToHome(event);
            }else{
                loginMessageLabel.setTextFill(Color.RED);
                loginMessageLabel.setText("Invalid login. Please try again!");
            }
        }
    }

}
