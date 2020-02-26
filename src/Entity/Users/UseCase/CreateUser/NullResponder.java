package Entity.Users.UseCase.CreateUser;

import Entity.Users.User;

public class NullResponder implements Responder {
    @Override
    public void userCreated(User user) {
    }

    @Override
    public void userExists(String login) {
    }
}
