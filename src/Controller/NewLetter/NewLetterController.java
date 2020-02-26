package Controller.NewLetter;

import Entity.Letters.Letter;
import Entity.Letters.UseCase.CreateLetter.Command;
import Entity.Letters.UseCase.CreateLetter.Responder;
import Services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class NewLetterController implements Responder {
    @FXML
    private TextField txtTo;

    @FXML
    private TextArea txtMessage;

    @FXML
    private Button btnSend;

    @FXML
    void sendMessage(ActionEvent event) {
        String receiver = txtTo.getText();
        String message = txtMessage.getText();
        Command command = new Command(
                Services.getUser(),
                message,
                receiver
        );
        command.setResponder(this);
        Services.getCreateLetter().execute(command);
    }

    @Override
    public void receiverDoesNotExist(String email) {
        Alert alert = new Alert(AlertType.INFORMATION,"User "+email+" to send letter to does not exist", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void messageEmpty() {
        Alert alert = new Alert(AlertType.INFORMATION,"Cannot send empty message", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void messageSent(Letter letter) {
        txtTo.clear();
        txtMessage.clear();
        Alert alert = new Alert(AlertType.INFORMATION,"Message sent", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void cantSendToSelf() {
        Alert alert = new Alert(AlertType.INFORMATION,"Cannot send message to self", ButtonType.OK);
        alert.showAndWait();
    }
}
