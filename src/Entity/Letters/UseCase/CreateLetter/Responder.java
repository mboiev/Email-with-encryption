package Entity.Letters.UseCase.CreateLetter;

import Entity.Letters.Letter;

public interface Responder {
    public void receiverDoesNotExist(String email);
    public void messageEmpty();
    public void messageSent(Letter letter);
    public void cantSendToSelf();
}
