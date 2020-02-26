package Entity.Users.UseCase.CreateUser;

import Entity.Users.User;

public interface Responder {

    public void userCreated(User user);
    public void userExists(String login);
}
