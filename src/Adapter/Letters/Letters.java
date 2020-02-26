package Adapter.Letters;

import Entity.Letters.Letter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Letters implements Entity.Letters.Letters {

    private EntityManager manager;
    private CriteriaBuilder builder;
    private CriteriaQuery<Letter> userCriteriaQuery;
    private Root<Letter> root;

    public Letters(EntityManager manager) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
        this.userCriteriaQuery = builder.createQuery(Letter.class);
        this.root = userCriteriaQuery.from(Letter.class);
    }

    @Override
    public void addLetter(Letter letter) {
        this.manager.persist(letter);
    }
}
