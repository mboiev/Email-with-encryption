package Adapter.Core;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Transaction implements Core.Transaction {
    private EntityManager manager;

    public Transaction(String persistenceUnitName) {

        this.manager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    }

    @Override
    public void begin() {
        this.manager.getTransaction().begin();
    }

    @Override
    public void rollback() {

        this.manager.getTransaction().rollback();
        this.manager.close();
    }

    @Override
    public void commit() {
        this.manager.getTransaction().commit();
    }

    public EntityManager getManager() {
        return this.manager;
    }
}
