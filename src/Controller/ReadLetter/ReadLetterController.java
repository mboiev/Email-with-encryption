package Controller.ReadLetter;

import Core.Utils.PrimeNumberUtil;
import Core.Utils.RSAUtils;
import Entity.Letters.ReadModel.Letters;
import Services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReadLetterController {
    @FXML
    private TextField txtFrom;

    @FXML
    private TextArea txtMessage;

    @FXML
    private Label lblDate;

    private Letters letter;

    public ReadLetterController(Letters letter){
        this.letter = letter;
    }

    @FXML
    public void initialize() throws IOException {

        txtFrom.setText(letter.getAuthor());

        txtMessage.setText(decode());
        Date date = new Date(TimeUnit.SECONDS.toMillis((long)letter.getDate()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        lblDate.setText(simpleDateFormat.format(date));
    }

    private String decode() throws IOException {
        byte[] byteMessage = (new BigInteger(this.letter.getLetter(),16)).toByteArray();
        String path = Services.getKeyPath();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String keysString = reader.readLine();
        String[] keys = keysString.split(";");
        RSAUtils rsaUtils = new RSAUtils(new PrimeNumberUtil());
        byte[] decryptedByteMessage = rsaUtils.rsaDecode(
                new BigInteger(byteMessage),
                new BigInteger(keys[0]),
                new BigInteger(keys[1])
        ).toByteArray();
        return new String(decryptedByteMessage);
    }
}
