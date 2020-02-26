package Entity.Letters.UseCase.CreateLetter;

import Entity.Users.User;

public class Command {
    private String receiver;
    private User author;
    private String letterText;
    private Responder responder;

    public Command(
            User author,
            String letterText,
            String receiver
    ){
        this.author = author;
        this.letterText = letterText;
        this.receiver = receiver;
        this.responder = new NullResponder();
    }

    public User getAuthor() {
        return author;
    }

    public String getLetterText() {
        return letterText;
    }

    public String getReceiver() {
        return receiver;
    }

    public Responder getResponder() {
        return responder;
    }

    public void setResponder(Responder responder) {
        this.responder = responder;
    }
}
