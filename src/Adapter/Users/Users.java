package Adapter.Users;

import Entity.Users.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Users implements Entity.Users.Users {


    private EntityManager manager;
    private CriteriaBuilder builder ;
    private CriteriaQuery<User> userCriteriaQuery ;
    private Root<User> root;

    public Users(
            EntityManager manager
    ) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
        this.userCriteriaQuery = builder.createQuery(User.class);
        this.root = userCriteriaQuery.from(User.class);
    }

    @Override
    public void addUser(User user) {
        this.manager.persist(user);
    }

    @Override
    public User findOneByLogin(String login) {
        try {
            CriteriaQuery<User> query = userCriteriaQuery.select(this.root).where(builder.equal(root.get("userName"), login));
            return this.manager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findOneByEmail(String email) {
        try{
            CriteriaQuery<User> query = userCriteriaQuery.select(this.root).where(builder.equal(root.get("email"), email));
            return this.manager.createQuery(query).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
