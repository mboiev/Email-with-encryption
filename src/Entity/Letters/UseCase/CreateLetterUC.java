package Entity.Letters.UseCase;

import Core.Transaction;
import Core.Utils.PrimeNumberUtil;
import Core.Utils.RSAUtils;
import Entity.Letters.Letter;
import Entity.Letters.Letters;
import Entity.Letters.UseCase.CreateLetter.Command;
import Entity.Users.User;
import Entity.Users.Users;
import Services.Services;

import java.math.BigInteger;

public class CreateLetterUC {
    private Transaction transaction;
    private Users users;
    private Letters letters;

    public CreateLetterUC(
            Transaction transaction,
            Users users,
            Letters letters
    ){
        this.transaction = transaction;
        this.users = users;
        this.letters = letters;
    }

    public void execute(Command command){
        this.transaction.begin();

        User receiver = this.users.findOneByEmail(command.getReceiver());
        if(receiver == null){
            this.transaction.rollback();
            command.getResponder().receiverDoesNotExist(command.getReceiver());
            return;
        }

        if(receiver == command.getAuthor()){
            this.transaction.rollback();
            command.getResponder().cantSendToSelf();
            return;
        }

        if(command.getLetterText().isEmpty()){
            this.transaction.rollback();
            command.getResponder().messageEmpty();
            return;
        }

        RSAUtils rsaUtils = new RSAUtils(new PrimeNumberUtil());
        String[] keys  = receiver.getPublicKey().split(";");
        byte[] byteMessage = command.getLetterText().getBytes();
        BigInteger bigIntMessage = new BigInteger(byteMessage);
        String letterText = rsaUtils.rsaEncode(
                bigIntMessage,
                new BigInteger(keys[0]),
                new BigInteger(keys[1])
        ).toString(16);
        Letter letter = new Letter(
                letterText,
                command.getAuthor(),
                receiver
        );

        this.letters.addLetter(letter);

        try{
            this.transaction.commit();
        }catch (Throwable error){
            this.transaction.rollback();
            return;
        }

        command.getResponder().messageSent(letter);
    }
}
