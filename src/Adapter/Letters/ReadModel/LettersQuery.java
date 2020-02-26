package Adapter.Letters.ReadModel;

import Entity.Letters.ReadModel.Letters;
import Entity.Letters.ReadModel.LettersQueryInterface;
import Entity.Users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class LettersQuery implements LettersQueryInterface {

    private EntityManager manager;

    public LettersQuery(
            EntityManager manager
    ) {
        this.manager = manager;
    }

    @Override
    public ArrayList<Letters> getEmailsForUser(User user) {
        Query query = this.manager.createNativeQuery(
                "SELECT l.letter_id, u.user_name, l.sent_time, l.letter_text FROM " +
                    "letter AS l LEFT JOIN user AS u ON l.author = u.user_id WHERE l.receiver = ?");
        query.setParameter(1, user.getUserID().toString());
        ArrayList<Letters> emailsForUser = new ArrayList<>();
        try {
            List queryResult = query.getResultList();
            for (Object result : queryResult) {
                Object[] columns = (Object[]) result;
                emailsForUser.add(
                        new Letters(
                                (Integer) columns[0],
                                (String) columns[1],
                                (Integer) columns[2],
                                (String) columns[3]
                        )
                );
            }
            return emailsForUser;
        } catch (Throwable error) {
            return emailsForUser;
        }

    }
}
