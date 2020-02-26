package Entity.Letters.ReadModel;

import Entity.Users.User;

import java.util.List;

public interface LettersQueryInterface {

    public List<Letters> getEmailsForUser(User user);
}
