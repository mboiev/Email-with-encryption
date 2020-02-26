package Controller.Users;

import Core.Utils.HashUtil;
import Entity.Users.UseCase.CreateUser.Command;
import Entity.Users.UseCase.CreateUser.Responder;
import Entity.Users.User;
import Services.Services;
import View.MainPage.MainPage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class UsersController implements Responder {

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtLogin;

    @FXML
    private Label lblInfo;

    @FXML
    void btnCreateUser(MouseEvent event) throws InterruptedException {

        String login = txtLogin.getText();
        String password = txtPassword.getText();
        Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        if(!textPattern.matcher(password).matches() || password.length()<8){
            Alert wrongCredentials = new Alert(AlertType.ERROR,"Password must consist of:\n- At least 8 character\n- At least 1 upper case letter\n- At least 1 number",ButtonType.OK);
            wrongCredentials.showAndWait();
            return;
        }
        Alert signUp = new Alert(AlertType.NONE);
        signUp.setTitle("Loading...");
        signUp.show();
        Command command = new Command(
                login,
                password
        );
        command.setResponder(this);
        Services.getCreateUser().execute(command);
        signUp.setResult(ButtonType.OK);
        signUp.close();
    }

    @FXML
    void btnSignInUser(MouseEvent event) throws Exception {
        Alert loggingIn = new Alert(AlertType.NONE);
        loggingIn.setTitle("Loading...");
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        loggingIn.show();
        User user = Services.getUsers().findOneByLogin(login);
        loggingIn.setResult(ButtonType.OK);
        loggingIn.close();
        if(user == null){
            Alert wrongCredentials = new Alert(AlertType.ERROR,"Username does not exist",ButtonType.OK);
            wrongCredentials.showAndWait();
        }
        else if (user.getPassword().equals(new HashUtil(password).messageToHash())) {
            Services.setUser(user);
            MainPage mainPage = new MainPage();
            Stage stage = new Stage();
            mainPage.start(stage);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            Alert wrongCredentials = new Alert(AlertType.ERROR,"Email and password do not match",ButtonType.OK);
            wrongCredentials.showAndWait();
        }

    }


    @Override
    public void userCreated(User user) {
        Alert wrongCredentials = new Alert(AlertType.INFORMATION,"User was created. Your address is: "+user.getEmail(),ButtonType.OK);
        wrongCredentials.showAndWait();
    }

    @Override
    public void userExists(String login) {
        Alert wrongCredentials = new Alert(AlertType.ERROR,"User with such username already exists",ButtonType.OK);
        wrongCredentials.showAndWait();
    }

}
