package Controller.MainPage;

import Controller.ReadLetter.ReadLetterController;
import Entity.Letters.ReadModel.Letters;
import Services.Services;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainPageController {
    @FXML
    private Button btnClose;

    @FXML
    private SubScene sbScene;

    @FXML
    private Button btnOptions;

    @FXML
    private ListView<Pane> lstMessages;

    @FXML
    private Button btnNewLetter;

    @FXML
    private Button btnFetchLetters;

    @FXML
    void startNewLetter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../View/NewLetter/NewLetter.fxml"));
        sbScene.setRoot(root);
        sbScene.setStyle("visibility: visible");
    }

    @FXML
    void openOptions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../View/Options/Options.fxml"));
        sbScene.setRoot(root);
        sbScene.setStyle("visibility: visible");
    }

    @FXML
    void fetchLetters(ActionEvent event) {
        if(Services.getKeyPath().equals(""))
        {
            Alert alert = new Alert(AlertType.INFORMATION,"Key not found. Please import a key through settings", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ArrayList<Letters> letters = Services.getLettersQuery().getEmailsForUser(Services.getUser());
        lstMessages.getItems().clear();
        letters.forEach(
            (
                (letter)->{
                    lstMessages.getItems().add(this.createPane(letter));

                }
            )
        );
        lstMessages.setStyle("-fx-control-inner-background:  rgb(28, 28, 30)");
    }

    @FXML
    void closeApplication(ActionEvent event) {
        System.exit(0);
    }

    private Pane createPane(Letters letter){
        Date date = new Date(TimeUnit.SECONDS.toMillis((long)letter.getDate()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Label labelFrom = new Label("From: "+letter.getAuthor());
        labelFrom.setStyle(
                "-fx-border-insets:20px; -fx-background-insets: 20px; -fx-padding: 20px"
        );

        Label labelDate = new Label("Date: "+simpleDateFormat.format(date));
        labelDate.setStyle(
                "-fx-border-insets:20px; -fx-background-insets: 20px; -fx-padding: 20px; "
        );

        Node[] nodes = {labelFrom,labelDate};

        VBox vBox = new VBox(nodes);

        Pane pane = new Pane(vBox);
        pane.setPrefSize(200,100.0);

        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent >() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                decodeEmail(letter);
            }
        });
        return pane;
    }

    private void decodeEmail(Letters letter){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../View/ReadLetter/ReadLetter.fxml"));
            loader.setController(new ReadLetterController(letter));
            Parent root = loader.load();
            sbScene.setRoot(root);
            sbScene.setStyle("visibility: visible");
        }catch (IOException error){
            Alert alert = new Alert(AlertType.INFORMATION,"Key not found. Please import a key through settings", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
