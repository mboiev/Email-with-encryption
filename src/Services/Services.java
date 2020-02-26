package Services;

import Adapter.Core.Transaction;
import Adapter.Letters.Letters;
import Adapter.Letters.ReadModel.LettersQuery;
import Adapter.Users.Users;
import Entity.Letters.UseCase.CreateLetterUC;
import Entity.Users.UseCase.CreateUserUC;
import Entity.Users.User;

public final class Services {
    private static User user;
    private static String keyPath = "";
    private static Transaction transaction = new Transaction("User");
    private static Users users = new Users(transaction.getManager());
    private static Letters letters = new Letters(transaction.getManager());
    private static CreateUserUC createUser = new CreateUserUC(
            transaction,
            users
    );
    private static CreateLetterUC createLetter = new CreateLetterUC(
            transaction,
            users,
            letters
    );
    private static LettersQuery lettersQuery = new LettersQuery(
            transaction.getManager()
    );


    private Services(){}

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Services.user = user;
    }

    public static String getKeyPath() {
        return keyPath;
    }

    public static void setKeyPath(String keyPath) {
        Services.keyPath = keyPath;
    }

    public static Users getUsers() {
        return users;
    }

    public static Letters getLetters() {
        return letters;
    }

    public static CreateUserUC getCreateUser() {
        return createUser;
    }

    public static CreateLetterUC getCreateLetter() {
        return createLetter;
    }

    public static LettersQuery getLettersQuery() {
        return lettersQuery;
    }
}
