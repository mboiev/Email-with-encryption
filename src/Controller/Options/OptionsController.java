package Controller.Options;

import Services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class OptionsController {
    @FXML
    private Button btnImport;

    @FXML
    void importFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Key File");
        File file = fileChooser.showOpenDialog(btnImport.getScene().getWindow());
        if (file != null) {
            Services.setKeyPath(file.getAbsolutePath());
        }

    }
}
