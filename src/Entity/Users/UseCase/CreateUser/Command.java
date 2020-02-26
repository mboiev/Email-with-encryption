package Entity.Users.UseCase.CreateUser;

public class Command {

    private String login;
    private String password;
    private Responder responder;

    public Command(
            String login,
            String password
    )
    {
        this.login = login;
        this.password = password;
        this.responder = new NullResponder();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Responder getResponder() {
        return responder;
    }

    public void setResponder(Responder responder) {
        this.responder = responder;
    }
}
