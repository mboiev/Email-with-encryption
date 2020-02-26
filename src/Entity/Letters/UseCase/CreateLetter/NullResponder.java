package Entity.Letters.UseCase.CreateLetter;

import Entity.Letters.Letter;

public class NullResponder  implements Responder{
    @Override
    public void receiverDoesNotExist(String email) {
    }

    @Override
    public void messageEmpty() {
    }

    @Override
    public void messageSent(Letter letter) {
    }

    @Override
    public void cantSendToSelf() {

    }
}
